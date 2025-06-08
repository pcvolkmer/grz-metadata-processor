CREATE TABLE IF NOT EXISTS tbl_case
(
    id                      int auto_increment primary key,
    local_case_id           varchar(255) not null,
    coverage_type           varchar(255) not null,
    submitter_id            varchar(16) not null,
    genomic_data_center_id  varchar(16) not null,
    clinical_data_center_id varchar(16) not null,
    disease_type            varchar(24),
    genomic_study_type      varchar(24),
    genomic_study_subtype   varchar(24),
    lab_name                varchar(255) not null
);

CREATE TABLE IF NOT EXISTS tbl_donor
(
    id              int auto_increment primary key,
    case_id         int,
    donor_pseudonym varchar(255) not null,
    gender          varchar(16),
    relation        varchar(16)

);

CREATE TABLE IF NOT EXISTS tbl_lab_data
(
    id                              int auto_increment primary key,
    donor_id                        int,
    einsendenummer                  varchar(255) unique,
    lab_data_name                   varchar(255) not null,
    sample_date                     varchar(16) not null,
    sample_conservation             varchar(255),
    sequence_type                   varchar(255),
    sequence_subtype                varchar(255),
    fragmentation_method            varchar(255),
    library_type                    varchar(255),
    library_prep_kit                varchar(255) not null,
    library_prep_kit_manufacturer   varchar(255) not null,
    sequencer_model                 varchar(255) not null,
    sequencer_manufacturer          varchar(255) not null,
    kit_name                        varchar(255) not null,
    kit_manufacturer                varchar(255) not null,
    enrichment_kit                  varchar(255) not null,
    enrichment_kit_manufacturer     varchar(255),
    sequencing_layout               varchar(255),
    tumor_cell_count                int,
    tumor_cell_count_method         varchar(255),
    bioinformatics_pipeline_name    varchar(255) not null,
    bioinformatics_pipeline_version varchar(255) not null,
    reference_genome                varchar(8)
);

CREATE TABLE IF NOT EXISTS tbl_file
(
    id                  int auto_increment primary key,
    lab_data_id         int,
    file_path           varchar(255) unique,
    file_type           varchar(8),
    file_checksum       varchar(64) unique,
    file_size_in_bytes  int
);
