--
-- PostgreSQL database dump
--

-- Dumped from database version 9.1.9
-- Dumped by pg_dump version 9.1.9
-- Started on 2016-03-02 15:26:13

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- TOC entry 26 (class 2615 OID 7262117)
-- Name: de_metas_procurement; Type: SCHEMA; Schema: -; Owner: metasfresh
--

CREATE SCHEMA de_metas_procurement;


-- ALTER SCHEMA de_metas_procurement OWNER TO metasfresh;

SET search_path = de_metas_procurement, pg_catalog;


-- View: pmm_purchasecandidate_weekly

-- DROP VIEW pmm_purchasecandidate_weekly;

CREATE OR REPLACE VIEW public.pmm_purchasecandidate_weekly AS 
SELECT 
	c.c_bpartner_id, 
	c.m_product_id, 
	date_trunc('week'::text, c.datepromised) AS datepromised, 
	sum(c.qtypromised) AS qtypromised, 
	sum(c.qtyordered) AS qtyordered, 
	c.ad_client_id, 
	min(c.ad_org_id) AS ad_org_id, 
	min(c.created) AS created, 
	0 AS createdby, 
	max(c.updated) AS updated, 
	0 AS updatedby, 
	'Y'::character(1) AS isactive
FROM public.pmm_purchasecandidate c
GROUP BY c.ad_client_id, c.c_bpartner_id, c.m_product_id, date_trunc('week'::text, c.datepromised);

--
-- TOC entry 2911 (class 1255 OID 7262125)
-- Dependencies: 7670 7688 26
-- Name: getpmm_purchasecandidate_weekly(public.pmm_purchasecandidate, integer); Type: FUNCTION; Schema: de_metas_procurement; Owner: adempiere
--

CREATE FUNCTION getpmm_purchasecandidate_weekly(p_candidate public.pmm_purchasecandidate, p_weekoffset integer DEFAULT 0) RETURNS public.pmm_purchasecandidate_weekly
    LANGUAGE sql STABLE
    AS $_$
	select w.*
	from PMM_PurchaseCandidate_Weekly w
	where true
	and w.C_BPartner_ID=($1).C_BPartner_ID
	and w.M_Product_ID=($1).M_Product_ID
	and w.DatePromised=date_trunc('week', ($1).DatePromised) + $2 * interval '1 week'
	limit 1
	;
$_$;


-- Completed on 2016-03-02 15:26:14

--
-- PostgreSQL database dump complete
--

