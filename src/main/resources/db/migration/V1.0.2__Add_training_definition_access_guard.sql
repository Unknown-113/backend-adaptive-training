CREATE TABLE training_definition_access_guard (
    training_definition_access_guard_id bigserial  NOT NULL,
    participant_ref_id                  bigint     NOT NULL,
    training_definition_id              bigint     NOT NULL,
    access_count                        int4       NOT NULL,
    banned                              boolean    NOT NULL DEFAULT false,
    banned_at                           timestamp  NULL,
    last_access_at                      timestamp  NOT NULL,
    PRIMARY KEY (training_definition_access_guard_id),
    UNIQUE (participant_ref_id, training_definition_id),
    FOREIGN KEY (training_definition_id) REFERENCES training_definition
);

CREATE INDEX training_definition_access_guard_definition_index
    ON training_definition_access_guard (training_definition_id);

CREATE SEQUENCE training_definition_access_guard_seq AS bigint INCREMENT 50 MINVALUE 1;
