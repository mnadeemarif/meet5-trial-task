create schema if not exists meet5;

CREATE TABLE IF NOT EXISTS meet5.users (
                               user_id SERIAL PRIMARY KEY,
                               username VARCHAR(50) NOT NULL UNIQUE,
                               email VARCHAR(100) NOT NULL UNIQUE,
                               first_name VARCHAR(50) NOT NULL,
                               last_name VARCHAR(50) NOT NULL,
                               age INTEGER CHECK (age >= 13 AND age <= 120),
                               created_at timestamptz DEFAULT CURRENT_TIMESTAMP,
                               modified_at timestamptz DEFAULT CURRENT_TIMESTAMP
                                );

CREATE TABLE IF NOT EXISTS meet5.user_interactions (
                                   interaction_id SERIAL PRIMARY KEY,
                                   source_user_id BIGINT not null REFERENCES meet5.users(user_id),
                                   target_user_id BIGINT not null REFERENCES meet5.users(user_id),
                                   interaction_type VARCHAR(20) not null CHECK (interaction_type IN ('VISIT', 'LIKE')),
                                   created_at timestamptz DEFAULT CURRENT_TIMESTAMP,
                                   modified_at timestamptz DEFAULT  CURRENT_TIMESTAMP
--                                    CONSTRAINT unique_interaction UNIQUE (source_user_id, target_user_id, interaction_type)
--                                 CONSTRAINT unique_interaction_like unique (source_user_id, target_user_id) where interaction_type = 'LIKE'
    );

CREATE UNIQUE INDEX IF NOT EXISTS unique_interaction_like
    ON meet5.user_interactions (source_user_id, target_user_id)
    WHERE interaction_type = 'LIKE';

CREATE TABLE IF NOT EXISTS meet5.fraud_detection_log (
                                     log_id SERIAL PRIMARY KEY,
                                     user_id BIGINT REFERENCES meet5.users(user_id),
                                     -- interaction_count INTEGER NOT NULL,
                                     -- interaction_window_start TIMESTAMP NOT NULL,
                                     -- interaction_window_end TIMESTAMP NOT NULL,
                                     detected_at timestamptz DEFAULT CURRENT_TIMESTAMP,
                                     is_flagged BOOLEAN DEFAULT FALSE
);

CREATE INDEX if not exists idx_user_interactions_source ON meet5.user_interactions(source_user_id);
CREATE INDEX if not exists idx_user_interactions_target ON meet5.user_interactions(target_user_id);
CREATE INDEX if not exists idx_fraud_detection_user ON meet5.fraud_detection_log(user_id);
CREATE INDEX if not exists idx_user_interactions_timestamp ON meet5.user_interactions(created_at);