ALTER TABLE terminal_session_token
    ADD COLUMN IF NOT EXISTS refresh_token TEXT,
    ADD COLUMN IF NOT EXISTS session_state VARCHAR(64),
    ADD COLUMN IF NOT EXISTS access_token VARCHAR(64);
