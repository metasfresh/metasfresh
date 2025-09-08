UPDATE m_costrevaluationline crl
SET costinglevel=(SELECT COALESCE(pca.costinglevel, cas.costinglevel)
                  FROM m_product p
                           INNER JOIN m_product_category_acct pca ON (
                              pca.m_product_category_id = p.m_product_category_id
                          AND pca.c_acctschema_id = (SELECT cr.c_acctschema_id FROM m_costrevaluation cr WHERE cr.m_costrevaluation_id = crl.m_costrevaluation_id)
                      )
                           INNER JOIN c_acctschema cas ON cas.c_acctschema_id = pca.c_acctschema_id
                  WHERE p.m_product_id = crl.m_product_id)
WHERE costinglevel IS NULL
;

