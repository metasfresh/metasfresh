
ALTER TABLE M_MeansOfTransportation
    DROP COLUMN IF EXISTS AD_Table_ID
;

ALTER TABLE M_MeansOfTransportation
    DROP COLUMN IF EXISTS CM_Template_ID
;

ALTER TABLE M_MeansOfTransportation
    DROP COLUMN IF EXISTS OtherClause
;

ALTER TABLE M_MeansOfTransportation
    DROP COLUMN IF EXISTS WhereClause
;


CREATE UNIQUE INDEX m_meansoftransportation_unique_value
    ON M_MeansOfTransportation (value)
;

