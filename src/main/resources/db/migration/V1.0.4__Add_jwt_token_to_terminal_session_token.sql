ALTER TABLE terminal_session_token
    ADD COLUMN jwt_token TEXT NOT NULL DEFAULT '';
