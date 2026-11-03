-- Umsatzsteuervoranmeldung: add the reduced-rate (7%) standard German VAT-advance-return codes
-- (Kennziffern) as C_VAT_Code entries on the standard tax records in the base accounting schema
-- 1000000, REGARDLESS of whether that schema is active.
--
-- Follow-up to 5825840 / 5825890, which coded the 19% and 0% signatures. The reference set those
-- scripts were derived from (dt204) had no 7% reduced-rate transactions, so the reduced-rate
-- Kennziffern were never coded. These three are official German UStVA Kennziffern (BMF/ELSTER), so
-- every German client with 7% goods needs them:
--   66 = Vorsteuerbetraege aus Rechnungen anderer Unternehmer (tax amount)
--   61 = Vorsteuerbetraege aus dem innergemeinschaftlichen Erwerb von Gegenstaenden
--   93 = steuerpflichtige innergemeinschaftliche Erwerbe zu 7% (base amount; its tax flows into 61)
--
-- Like 5825890 this codes base schema 1000000 whether active or inactive; the NOT EXISTS guard makes
-- it a no-op wherever the rows already exist. Matching is by the tax SEMANTIC SIGNATURE (Rate,
-- C_TaxCategory_ID, C_Country_ID, TypeOfDestCountry, EN16931VATCategory, IsReverseCharge) and resolves
-- to exactly ONE canonical tax per signature (DISTINCT ON, preferring the active / most-current tax,
-- deterministic tie-break by C_Tax_ID); a signature with no matching tax on the instance inserts
-- nothing there. AD_Org_ID=0 (client-level). ValidFrom=1999-12-31 / ValidTo=NULL ("always valid").
--
-- PK: each refcode row carries a fixed C_VAT_Code_ID from the central ID server (idserver.metas.de,
-- TABLE=c_vat_code), allocated 2026-10-05: 540184-540186. Central-server ids are < 1000000 and never
-- collide with instance-local (native-sequence) c_vat_code ids, which start at 1000000.

WITH refcode(rate, c_taxcategory_id, c_country_id, typeofdestcountry, en16931vatcategory, isreversecharge, amounttype, issotrx, vatcode, c_vat_code_id) AS (
    VALUES
        -- 7% (DE) domestic, reduced rate (EN16931 S): Vorsteuer (tax amount)
        ( 7, 1000010, 101, 'DOMESTIC',            'S', 'N', 'T', 'N', '66', 540184),
        -- 7% (EU) reduced-rate intra-community acquisition (EN16931 K): Vorsteuer from i.g. Erwerb (tax)
        ( 0, 1000010, 101, 'WITHIN_COUNTRY_AREA', 'K', 'N', 'T', 'N', '61', 540185),
        -- 7% (EU) reduced-rate intra-community acquisition (EN16931 K): taxable i.g. Erwerb (base amount)
        ( 0, 1000010, 101, 'WITHIN_COUNTRY_AREA', 'K', 'N', 'N', 'N', '93', 540186)
),
-- resolve each refcode signature to exactly ONE canonical tax on the base schema 1000000 (active OR
-- inactive): prefer the active tax, then the most-current (open-ended / latest ValidTo, then latest
-- ValidFrom), deterministic tie-break by the newest C_Tax_ID.
matched AS (
    SELECT DISTINCT ON (r.c_vat_code_id)
           r.c_vat_code_id, r.vatcode, r.amounttype, r.issotrx,
           t.ad_client_id, t.c_tax_id, s.c_acctschema_id
    FROM refcode r
    JOIN c_tax t
          ON t.rate               = r.rate
         AND t.c_taxcategory_id   = r.c_taxcategory_id
         AND t.c_country_id       = r.c_country_id
         AND t.typeofdestcountry  = r.typeofdestcountry
         AND t.en16931vatcategory = r.en16931vatcategory
         AND t.isreversecharge    = r.isreversecharge
    JOIN c_acctschema s
          ON s.ad_client_id     = t.ad_client_id
         AND s.c_acctschema_id  = 1000000                                   -- base schema only, active OR inactive (org schemas handled in the customer repo)
    ORDER BY r.c_vat_code_id, t.isactive DESC, t.validto DESC NULLS FIRST, t.validfrom DESC, t.c_tax_id DESC
)
INSERT INTO c_vat_code (
    c_vat_code_id, ad_client_id, ad_org_id, c_acctschema_id, c_tax_id,
    vatcode, amounttype, issotrx, validfrom, validto, description, isactive,
    created, createdby, updated, updatedby
)
SELECT m.c_vat_code_id /*From ID Server*/,
       m.ad_client_id, 0, m.c_acctschema_id, m.c_tax_id,
       m.vatcode, m.amounttype, m.issotrx,
       TO_TIMESTAMP('1999-12-31 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), NULL, NULL, 'Y',
       TO_TIMESTAMP('2026-10-05 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-10-05 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM matched m
WHERE NOT EXISTS (
        SELECT 1
        FROM c_vat_code x
        WHERE x.c_acctschema_id = m.c_acctschema_id
          AND x.c_tax_id        = m.c_tax_id
          AND x.amounttype      = m.amounttype
          AND x.issotrx         = m.issotrx
          AND x.vatcode         = m.vatcode
          AND x.isactive        = 'Y'
      );
