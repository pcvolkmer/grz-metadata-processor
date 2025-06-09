CREATE TABLE IF NOT EXISTS tbl_lab_data_profile
(
    id                              serial,
    profile_name                    varchar(255),
    library_type                    varchar(255),
    library_prep_kit                varchar(255) not null,
    library_prep_kit_manufacturer   varchar(255) not null,
    sequencer_model                 varchar(255) not null,
    sequencer_manufacturer          varchar(255) not null,
    kit_name                        varchar(255) not null,
    kit_manufacturer                varchar(255) not null,
    enrichment_kit                  varchar(255) not null,
    enrichment_kit_manufacturer     varchar(255),
    PRIMARY KEY (id)
);

ALTER TABLE tbl_lab_data ADD COLUMN profile int;
