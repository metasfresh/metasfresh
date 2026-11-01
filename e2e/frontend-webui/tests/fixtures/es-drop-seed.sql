-- Seed for the ES->Postgres replacement E2E (es-drop-fts-and-kpi.spec.js). Idempotent: removes its own rows first.
--   partner  value ESDROPBP1  name "Zyxquarta Handels GmbH"
--   product  value ESDROPPRD1 name "Zyxbanane Bio Spezialsorte"
--   sales orders    IN period 100.00 + 250.50 (sum 350.50), OUT of period 999.99 (30 days ago, must not be counted)
--   purchase orders IN period  40.25 +  60.00 (sum 100.25)
-- Periods follow the two dashboard items of dashboard 540000: "Purchase net amt this week" = last 7 days,
-- "Sales orders net amt last week" = the 7 days before that (item 1000020 has es_timerange P-7D), so the sales
-- orders are dated 9 and 11 days ago and the purchase orders 1 and 3 days ago.
-- Orders are committed with valid FKs: every mandatory FK column points at the first existing row of its target table.
\set ON_ERROR_STOP on
BEGIN;

-- the Elasticsearch KPI dashboard ships inactive; the tiles only render when it is the active default dashboard
UPDATE webui_dashboard SET isactive='Y', isdefault='Y' WHERE webui_dashboard_id=540000;

DELETE FROM c_orderline WHERE c_order_id IN (SELECT c_order_id FROM c_order WHERE documentno LIKE 'ESDROP-%');
DELETE FROM c_order     WHERE documentno LIKE 'ESDROP-%';
DELETE FROM c_bpartner_fts WHERE c_bpartner_id IN (SELECT c_bpartner_id FROM c_bpartner WHERE value='ESDROPBP1');
DELETE FROM c_bpartner  WHERE value='ESDROPBP1';
DELETE FROM m_product_fts WHERE m_product_id IN (SELECT m_product_id FROM m_product WHERE value='ESDROPPRD1');
DELETE FROM m_product   WHERE value='ESDROPPRD1';

-- precondition: the KPI sums every order of the period, so foreign orders would falsify the expected 350.50 / 100.25
DO $$
BEGIN
  IF EXISTS (SELECT 1 FROM c_order WHERE dateordered >= current_date - 14) THEN
    RAISE EXCEPTION 'seed precondition failed: foreign orders exist in the last 14 days; the KPI tiles would not show 350.50 / 100.25';
  END IF;
END $$;

-- partner + product: clone an existing row (keeps all mandatory columns) and rename
CREATE TEMP TABLE _bp ON COMMIT DROP AS SELECT * FROM c_bpartner ORDER BY c_bpartner_id LIMIT 1;
UPDATE _bp SET c_bpartner_id=(SELECT max(c_bpartner_id)+1 FROM c_bpartner), value='ESDROPBP1', name='Zyxquarta Handels GmbH', name2=NULL, companyname=NULL, ad_orgbp_id=NULL;
INSERT INTO c_bpartner SELECT * FROM _bp;

CREATE TEMP TABLE _p ON COMMIT DROP AS SELECT * FROM m_product ORDER BY m_product_id LIMIT 1;
UPDATE _p SET m_product_id=(SELECT max(m_product_id)+1 FROM m_product), value='ESDROPPRD1', name='Zyxbanane Bio Spezialsorte', description=NULL;
INSERT INTO m_product SELECT * FROM _p;

-- orders: synthesize one template row per table (no orders exist on a bare DB), mandatory FKs -> first existing target row
CREATE TEMP TABLE _o  (LIKE c_order INCLUDING DEFAULTS) ON COMMIT DROP;
CREATE TEMP TABLE _ol (LIKE c_orderline INCLUDING DEFAULTS) ON COMMIT DROP;
DO $$
DECLARE t text; cols text; vals text; r record; v text;
BEGIN
  FOREACH t IN ARRAY ARRAY['c_order','c_orderline'] LOOP
    cols := ''; vals := '';
    FOR r IN SELECT * FROM (SELECT c.column_name, c.data_type, c.character_maximum_length AS len, c.is_nullable, c.column_default, c.ordinal_position,
                    (SELECT format('(SELECT %I FROM %I ORDER BY 1 LIMIT 1)', af.attname, cl.relname)
                       FROM pg_constraint k JOIN pg_class cl ON cl.oid=k.confrelid
                       JOIN pg_attribute af ON af.attrelid=k.confrelid AND af.attnum=k.confkey[1]
                       JOIN pg_attribute a ON a.attrelid=k.conrelid AND a.attnum=k.conkey[1]
                      WHERE k.contype='f' AND k.conrelid=t::regclass AND array_length(k.conkey,1)=1 AND a.attname=c.column_name LIMIT 1) AS fk
             FROM information_schema.columns c
             WHERE c.table_schema='public' AND c.table_name=t) x
             WHERE x.is_nullable='NO' AND (x.column_default IS NULL OR x.fk IS NOT NULL) ORDER BY x.ordinal_position LOOP
      cols := cols || CASE WHEN cols='' THEN '' ELSE ',' END || quote_ident(r.column_name);
      vals := vals || CASE WHEN vals='' THEN '' ELSE ',' END ||
        CASE WHEN t='c_orderline' AND r.column_name='c_order_id' THEN '0'
             WHEN r.fk IS NOT NULL THEN r.fk
             WHEN r.data_type IN ('numeric','integer','bigint','smallint') THEN '0'
             WHEN r.data_type IN ('character','character varying','text') THEN CASE WHEN coalesce(r.len,2)=1 THEN '''N''' ELSE '''x''' END
             WHEN r.data_type='date' THEN 'current_date'
             WHEN r.data_type LIKE 'timestamp%' THEN 'now()'
             ELSE 'NULL' END;
    END LOOP;
    EXECUTE format('INSERT INTO %I (%s) VALUES (%s)', CASE t WHEN 'c_order' THEN '_o' ELSE '_ol' END, cols, vals);
  END LOOP;
END $$;
UPDATE _o SET c_bpartner_id=(SELECT c_bpartner_id FROM c_bpartner WHERE value='ESDROPBP1'),
  ad_client_id=(SELECT ad_client_id FROM _bp), ad_org_id=(SELECT ad_org_id FROM _bp);
UPDATE _ol SET m_product_id=(SELECT m_product_id FROM m_product WHERE value='ESDROPPRD1'),
  ad_client_id=(SELECT ad_client_id FROM _bp), ad_org_id=(SELECT ad_org_id FROM _bp);

CREATE TEMP TABLE _seed(tag text, issotrx char(1), dateordered date, amt numeric) ON COMMIT DROP;
INSERT INTO _seed VALUES
 ('S1','Y', current_date - 9,  100.00),
 ('S2','Y', current_date - 11, 250.50),
 ('S3','Y', current_date - 30, 999.99),
 ('P1','N', current_date - 1,   40.25),
 ('P2','N', current_date - 3,   60.00);

DO $$
DECLARE r record; v_oid numeric; v_olid numeric;
BEGIN
  FOR r IN SELECT * FROM _seed ORDER BY tag LOOP
    SELECT coalesce(max(c_order_id),0)+1 INTO v_oid FROM c_order;
    SELECT coalesce(max(c_orderline_id),0)+1 INTO v_olid FROM c_orderline;
    UPDATE _o  SET c_order_id=v_oid, issotrx=r.issotrx, dateordered=r.dateordered, documentno='ESDROP-'||r.tag;
    INSERT INTO c_order SELECT * FROM _o;
    UPDATE _ol SET c_orderline_id=v_olid, c_order_id=v_oid, linenetamt=r.amt, dateordered=r.dateordered;
    INSERT INTO c_orderline SELECT * FROM _ol;
  END LOOP;
END $$;
COMMIT;

-- ground truth of the seed + FTS rows (fails the seed run if anything is off)
DO $$
DECLARE v_sales numeric; v_purchase numeric;
BEGIN
  SELECT sum(ol.linenetamt) INTO v_sales    FROM c_orderline ol JOIN c_order o USING (c_order_id) WHERE o.documentno LIKE 'ESDROP-%' AND o.issotrx='Y' AND o.dateordered BETWEEN current_date - 14 AND current_date - 7;
  SELECT sum(ol.linenetamt) INTO v_purchase FROM c_orderline ol JOIN c_order o USING (c_order_id) WHERE o.documentno LIKE 'ESDROP-%' AND o.issotrx='N' AND o.dateordered >= current_date - 7;
  IF v_sales IS DISTINCT FROM 350.50 OR v_purchase IS DISTINCT FROM 100.25 THEN RAISE EXCEPTION 'seed error: sales % / purchase %', v_sales, v_purchase; END IF;
  IF NOT EXISTS (SELECT 1 FROM c_bpartner_fts f JOIN c_bpartner b USING (c_bpartner_id) WHERE b.value='ESDROPBP1') THEN RAISE EXCEPTION 'seed error: partner not in c_bpartner_fts'; END IF;
  IF NOT EXISTS (SELECT 1 FROM m_product_fts f JOIN m_product p USING (m_product_id) WHERE p.value='ESDROPPRD1') THEN RAISE EXCEPTION 'seed error: product not in m_product_fts'; END IF;
END $$;
