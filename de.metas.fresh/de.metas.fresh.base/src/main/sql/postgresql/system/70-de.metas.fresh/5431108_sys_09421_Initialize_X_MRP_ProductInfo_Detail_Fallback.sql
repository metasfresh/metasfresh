
--when we rollout this task, the fallback-data is complete, at least for today
--most probably also for the next 3 days, but why assume when we can make sure.
INSERT INTO X_MRP_ProductInfo_Detail_MV
SELECT * 
FROM X_MRP_ProductInfo_Detail_Fallback_V(now()::date, (now() + interval '9 days')::date);
