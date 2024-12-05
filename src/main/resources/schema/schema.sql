CREATE TABLE IF NOT EXISTS users (
                               user_id BIGINT PRIMARY KEY,
                               username VARCHAR(50) NOT NULL UNIQUE,
                               email VARCHAR(100) NOT NULL UNIQUE,
                               first_name VARCHAR(50) NOT NULL,
                               last_name VARCHAR(50) NOT NULL,
                               age INTEGER CHECK (age >= 13 AND age <= 120),
                               created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                               modified_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                               last_active_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS user_interactions (
                                   interaction_id BIGINT PRIMARY KEY,
                                   source_user_id BIGINT REFERENCES users(user_id),
                                   target_user_id BIGINT REFERENCES users(user_id),
                                   interaction_type VARCHAR(20) CHECK (interaction_type IN ('VISIT', 'LIKE')),
                                   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                   modified_at TIMESTAMP DEFAULT  CURRENT_TIMESTAMP,
                                   CONSTRAINT unique_interaction UNIQUE (source_user_id, target_user_id, interaction_type)
);

CREATE TABLE IF NOT EXISTS fraud_detection_log (
                                     log_id BIGINT PRIMARY KEY,
                                     user_id BIGINT REFERENCES users(user_id),
                                     interaction_count INTEGER NOT NULL,
                                     interaction_window_start TIMESTAMP NOT NULL,
                                     interaction_window_end TIMESTAMP NOT NULL,
                                     detected_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                     is_flagged BOOLEAN DEFAULT FALSE
);

CREATE INDEX idx_user_interactions_source ON user_interactions(source_user_id);
CREATE INDEX idx_user_interactions_target ON user_interactions(target_user_id);
CREATE INDEX idx_fraud_detection_user ON fraud_detection_log(user_id);
CREATE INDEX idx_user_interactions_timestamp ON user_interactions(created_at);