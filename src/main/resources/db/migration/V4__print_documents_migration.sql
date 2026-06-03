CREATE TABLE print_jet
(
    id       smallserial PRIMARY KEY,
    jet_name VARCHAR(50) NOT NULL
);

CREATE TABLE print_documents
(
    id                UUID REFERENCES buses (id) PRIMARY KEY,
    status            VARCHAR(10) NOT NULL DEFAULT 'pending',
    CONSTRAINT chk_document_status CHECK (status IN ('pending', 'printed'))
);

CREATE TABLE printed_jets
(
    document_id UUID REFERENCES print_documents (id) ON DELETE CASCADE,
    jet_id      SMALLINT REFERENCES print_jet (id) ON DELETE CASCADE,
    print_date  TIMESTAMP DEFAULT NOW(),
    PRIMARY KEY (document_id, jet_id)
);