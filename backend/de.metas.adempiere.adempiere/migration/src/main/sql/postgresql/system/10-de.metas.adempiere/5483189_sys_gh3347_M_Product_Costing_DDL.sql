drop view if exists RV_Product_Costing;
drop table if exists M_Product_Costing;



/*
CREATE TABLE public.m_product_costing
(
  m_product_id numeric(10,0) NOT NULL,
  c_acctschema_id numeric(10,0) NOT NULL,
  ad_client_id numeric(10,0) NOT NULL,
  ad_org_id numeric(10,0) NOT NULL,
  isactive character(1) NOT NULL DEFAULT 'Y'::bpchar,
  created timestamp with time zone NOT NULL DEFAULT now(),
  createdby numeric(10,0) NOT NULL,
  updated timestamp with time zone NOT NULL DEFAULT now(),
  updatedby numeric(10,0) NOT NULL,
  currentcostprice numeric NOT NULL DEFAULT 0,
  futurecostprice numeric NOT NULL DEFAULT 0,
  coststandard numeric NOT NULL DEFAULT 0,
  coststandardpoqty numeric NOT NULL DEFAULT 0,
  coststandardpoamt numeric NOT NULL DEFAULT 0,
  coststandardcumqty numeric NOT NULL DEFAULT 0,
  coststandardcumamt numeric NOT NULL DEFAULT 0,
  costaverage numeric NOT NULL DEFAULT 0,
  costaveragecumqty numeric NOT NULL DEFAULT 0,
  costaveragecumamt numeric NOT NULL DEFAULT 0,
  pricelastpo numeric NOT NULL DEFAULT 0,
  pricelastinv numeric NOT NULL DEFAULT 0,
  totalinvqty numeric NOT NULL DEFAULT 0,
  totalinvamt numeric NOT NULL DEFAULT 0,
  CONSTRAINT m_product_costing_pkey PRIMARY KEY (m_product_id, c_acctschema_id),
  CONSTRAINT cacctschema_mproductcosting FOREIGN KEY (c_acctschema_id)
      REFERENCES public.c_acctschema (c_acctschema_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED,
  CONSTRAINT mproduct_mproductcosting FOREIGN KEY (m_product_id)
      REFERENCES public.m_product (m_product_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED,
  CONSTRAINT m_product_costing_isactive_check CHECK (isactive = ANY (ARRAY['Y'::bpchar, 'N'::bpchar]))
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.m_product_costing
  OWNER TO metasfresh;
*/




/*
CREATE OR REPLACE VIEW public.rv_product_costing AS 
 SELECT pc.m_product_id,
    pc.c_acctschema_id,
    p.value,
    p.name,
    p.m_product_category_id,
    pc.ad_client_id,
    pc.ad_org_id,
    pc.isactive,
    pc.created,
    pc.createdby,
    pc.updated,
    pc.updatedby,
    pc.currentcostprice,
    pc.futurecostprice,
    pc.coststandard,
    pc.coststandardpoqty,
    pc.coststandardpoamt,
        CASE
            WHEN pc.coststandardpoqty = 0::numeric THEN 0::numeric
            ELSE pc.coststandardpoamt / pc.coststandardpoqty
        END AS coststandardpodiff,
    pc.coststandardcumqty,
    pc.coststandardcumamt,
        CASE
            WHEN pc.coststandardcumqty = 0::numeric THEN 0::numeric
            ELSE pc.coststandardcumamt / pc.coststandardcumqty
        END AS coststandardinvdiff,
    pc.costaverage,
    pc.costaveragecumqty,
    pc.costaveragecumamt,
    pc.totalinvqty,
    pc.totalinvamt,
        CASE
            WHEN pc.totalinvqty = 0::numeric THEN 0::numeric
            ELSE pc.totalinvamt / pc.totalinvqty
        END AS totalinvcost,
    pc.pricelastpo,
    pc.pricelastinv
   FROM m_product_costing pc
     JOIN m_product p ON pc.m_product_id = p.m_product_id;

ALTER TABLE public.rv_product_costing
  OWNER TO metasfresh;
*/