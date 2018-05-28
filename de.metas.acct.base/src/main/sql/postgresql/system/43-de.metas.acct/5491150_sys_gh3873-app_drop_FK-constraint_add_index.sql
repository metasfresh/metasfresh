-- 2018-04-17T15:52:27.999
-- URL zum Konzept
UPDATE AD_Column SET DDL_NoForeignKey='Y', TechnicalNote='We have no FK-constraint, because Counterpart_Fact_Acct_ID => Fact_Acct_ID is always very "local".
I.e. only Fact_Acct records that are about the same document are linked via this column. 
On the other hand, deleting a Fact_Acct records would be very expensive if this constraint was in place. And we don''t think it''s worth another index.
See https://github.com/metasfresh/metasfresh/issues/3873',Updated=TO_TIMESTAMP('2018-04-17 15:52:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=559124
;

--
-- DDL
--
--drop FK constraint
ALTER TABLE public.fact_acct DROP CONSTRAINT IF EXISTS counterpartfactacct_factacct;

-- another one that's rather slow:
CREATE INDEX IF NOT EXISTS m_cost_m_product_id
   ON public.m_cost (m_product_id ASC NULLS LAST);
