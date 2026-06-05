ALTER TABLE print_documents DROP CONSTRAINT
    IF EXISTS print_documents_id_fkey;

ALTER TABLE print_documents ADD CONSTRAINT print_documents_id_fkey
FOREIGN KEY(id) references buses(id) ON DELETE CASCADE;