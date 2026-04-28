
DROP INDEX IF EXISTS c_paymentterm_name;


--old value c_paymentterm (ad_client_id, name)
CREATE UNIQUE INDEX IF NOT EXISTS c_paymentterm_name
    ON c_paymentterm (ad_client_id, name) WHERE isactive='Y'
;

