CREATE OR REPLACE VIEW report.fresh_attributeprice AS 
 SELECT pp.m_productprice_id,
    COALESCE(ai.m_attributesetinstance_id,pp.m_attributesetinstance_id) as m_attributesetinstance_id,
    pp.pricestd,
    pp.isactive,
    pp.m_hu_pi_item_product_id,
    string_agg(av.value::text, ', '::text ORDER BY (av.value::text)) AS attributes,
    (COALESCE(pp.m_hu_pi_item_product_id || ' '::text, ''::text) || COALESCE(pp.isdefault::text || ' '::text, ''::text)) || COALESCE(string_agg((ai.m_attribute_id::text || ' '::text) || ai.m_attributevalue_id::text, ','::text ORDER BY ai.m_attribute_id), ''::text) AS signature
   FROM m_productprice pp
     LEFT JOIN m_attributeinstance ai ON pp.m_attributesetinstance_id = ai.m_attributesetinstance_id AND ai.isactive = 'Y'::bpchar
     LEFT JOIN ( SELECT av_1.isactive,
            av_1.m_attributevalue_id,
                CASE
                    WHEN a.value::text = '1000015'::text AND av_1.value::text = '01'::text THEN NULL::character varying
                    WHEN a.value::text = '1000015'::text AND av_1.value IS NOT NULL THEN 'AdR'::character varying
                    WHEN a.value::text = '1000001'::text THEN av_1.value
                    ELSE av_1.name
                END AS value
           FROM m_attributevalue av_1
             LEFT JOIN m_attribute a ON av_1.m_attribute_id = a.m_attribute_id) av ON ai.m_attributevalue_id = av.m_attributevalue_id AND av.isactive = 'Y'::bpchar AND av.value IS NOT NULL
  WHERE pp.isactive = 'Y'::bpchar
  GROUP BY pp.m_productprice_id, ai.m_attributesetinstance_id, pp.m_attributesetinstance_id, pp.pricestd, pp.isactive, pp.m_hu_pi_item_product_id;

ALTER TABLE report.fresh_attributeprice
  OWNER TO metasfresh;
COMMENT ON VIEW report.fresh_attributeprice
  IS 'This View is supposed to be used by the View RV_fresh_PriceList_Comparison and retrieves the Attribute price together with a rendered list of Attributes and a signature. Attribute prices of different Price list version but with the same attributes and packin instruction config can be matched and therefore compared with this.';


