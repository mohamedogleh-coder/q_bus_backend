
ALTER TABLE providers
    ADD COLUMN prefix VARCHAR(10);

update providers set prefix='63' where id=1;
update providers set prefix='65' where id=2;
update providers set prefix='67' where id=3;

ALTER TABLE providers ALTER  COLUMN  prefix SET NOT NULL ;
