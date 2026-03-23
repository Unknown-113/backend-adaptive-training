ALTER TABLE task
    ADD COLUMN dynamic_flag_enabled boolean NOT NULL DEFAULT false;

ALTER TABLE task
    ADD COLUMN dynamic_flag_interval_minutes int4;

ALTER TABLE task
    ADD COLUMN dynamic_flag_secret varchar(255);

ALTER TABLE task
    ALTER COLUMN answer DROP NOT NULL;
