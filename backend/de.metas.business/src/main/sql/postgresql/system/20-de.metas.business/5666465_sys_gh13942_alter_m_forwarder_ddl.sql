
ALTER TABLE M_Forwarder
    DROP COLUMN IF EXISTS AD_Table_ID
;

ALTER TABLE M_Forwarder
    DROP COLUMN IF EXISTS CM_Template_ID
;

ALTER TABLE M_Forwarder
    DROP COLUMN IF EXISTS OtherClause
;

ALTER TABLE M_Forwarder
    DROP COLUMN IF EXISTS WhereClause
;


CREATE UNIQUE INDEX m_forwarder_unique_value
    ON m_forwarder (value)
;


