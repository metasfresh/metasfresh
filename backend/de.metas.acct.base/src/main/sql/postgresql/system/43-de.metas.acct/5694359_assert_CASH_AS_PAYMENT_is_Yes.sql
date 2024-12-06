DO
$$
    DECLARE
        r record;
    BEGIN
        FOR r IN (SELECT name, value FROM ad_sysconfig WHERE name = 'CASH_AS_PAYMENT')
            LOOP
                IF (r.value != 'Y') THEN
                    RAISE EXCEPTION 'Sysconfig CASH_AS_PAYMENT is expected to be set to Y before removing it.';
                END IF;
            END LOOP;
    END
$$
;
