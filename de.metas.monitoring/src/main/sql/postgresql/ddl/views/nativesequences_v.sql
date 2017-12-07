
CREATE OR REPLACE VIEW "de.metas.monitoring".nativesequences_v AS 
 SELECT sequences.sequence_name,
    sequences.maximum_value,
    nextval(((('"'::text || sequences.sequence_schema::text) || '".'::text) || sequences.sequence_name::text)::regclass) AS nextval,
    currval(((('"'::text || sequences.sequence_schema::text) || '".'::text) || sequences.sequence_name::text)::regclass) AS currval,
    round((sequences.maximum_value::numeric - currval(((('"'::text || sequences.sequence_schema::text) || '".'::text) || sequences.sequence_name::text)::regclass)::numeric) / sequences.maximum_value::numeric * 100::numeric, 2) AS sequence_range_free_percent
   FROM information_schema.sequences
