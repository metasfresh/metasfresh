

ALTER TABLE A_Asset_Acct RENAME COLUMN a_disposal_gain TO a_disposal_gain_OLD;
ALTER TABLE A_Asset_Acct ADD COLUMN a_disposal_gain  NUMERIC(10);
UPDATE A_Asset_Acct SET a_disposal_gain=a_disposal_gain_OLD::NUMERIC(10);
COMMIT;
ALTER TABLE A_Asset_Acct DROP COLUMN a_disposal_gain_OLD;

ALTER TABLE A_Asset_Acct RENAME COLUMN a_disposal_loss TO a_disposal_loss_OLD;
ALTER TABLE A_Asset_Acct ADD COLUMN a_disposal_loss  NUMERIC(10);
UPDATE A_Asset_Acct SET a_disposal_loss=a_disposal_loss_OLD::NUMERIC(10);
COMMIT;
ALTER TABLE A_Asset_Acct DROP COLUMN a_disposal_loss_old;

ALTER TABLE A_Asset_Acct RENAME COLUMN a_disposal_revenue TO a_disposal_revenue_OLD;
ALTER TABLE A_Asset_Acct ADD COLUMN a_disposal_revenue  NUMERIC(10);
UPDATE A_Asset_Acct SET a_disposal_revenue=a_disposal_revenue_OLD::NUMERIC(10);
COMMIT;
ALTER TABLE A_Asset_Acct DROP COLUMN a_disposal_revenue_OLD;

ALTER TABLE A_Asset_Acct RENAME COLUMN A_Reval_Accumdep_Offset_Cur TO A_Reval_Accumdep_Offset_Cur_OLD;
ALTER TABLE A_Asset_Acct ADD COLUMN A_Reval_Accumdep_Offset_Cur  NUMERIC(10);
UPDATE A_Asset_Acct SET A_Reval_Accumdep_Offset_Cur=A_Reval_Accumdep_Offset_Cur_OLD::NUMERIC(10);
COMMIT;
ALTER TABLE A_Asset_Acct DROP COLUMN A_Reval_Accumdep_Offset_Cur_OLD;

ALTER TABLE A_Asset_Acct RENAME COLUMN a_reval_accumdep_offset_prior TO a_reval_accumdep_offset_prior_OLD;
ALTER TABLE A_Asset_Acct ADD COLUMN a_reval_accumdep_offset_prior  NUMERIC(10);
UPDATE A_Asset_Acct SET a_reval_accumdep_offset_prior=a_reval_accumdep_offset_prior_OLD::NUMERIC(10);
COMMIT;
ALTER TABLE A_Asset_Acct DROP COLUMN a_reval_accumdep_offset_prior_OLD;

ALTER TABLE A_Asset_Acct RENAME COLUMN a_reval_cost_offset TO a_reval_cost_offset_OLD;
ALTER TABLE A_Asset_Acct ADD COLUMN a_reval_cost_offset  NUMERIC(10);
UPDATE A_Asset_Acct SET a_reval_cost_offset=a_reval_cost_offset_OLD::NUMERIC(10);
COMMIT;
ALTER TABLE A_Asset_Acct DROP COLUMN a_reval_cost_offset_OLD;

ALTER TABLE A_Asset_Acct RENAME COLUMN a_reval_cost_offset_prior TO a_reval_cost_offset_prior_OLD;
ALTER TABLE A_Asset_Acct ADD COLUMN a_reval_cost_offset_prior  NUMERIC(10);
UPDATE A_Asset_Acct SET a_reval_cost_offset_prior=a_reval_cost_offset_prior_OLD::NUMERIC(10);
COMMIT;
ALTER TABLE A_Asset_Acct DROP COLUMN a_reval_cost_offset_prior_OLD;

ALTER TABLE A_Asset_Acct RENAME COLUMN a_reval_depexp_offset TO a_reval_depexp_offset_OLD;
ALTER TABLE A_Asset_Acct ADD COLUMN a_reval_depexp_offset  NUMERIC(10);
UPDATE A_Asset_Acct SET a_reval_depexp_offset=a_reval_depexp_offset_OLD::NUMERIC(10);
COMMIT;
ALTER TABLE A_Asset_Acct DROP COLUMN a_reval_depexp_offset_OLD;

ALTER TABLE A_Asset_Change RENAME COLUMN a_asset_spread_type TO a_asset_spread_type_OLD;
ALTER TABLE A_Asset_Change ADD COLUMN a_asset_spread_type  NUMERIC(10);
UPDATE A_Asset_Change SET a_asset_spread_type=a_asset_spread_type_OLD::NUMERIC(10);
COMMIT;
ALTER TABLE A_Asset_Change DROP COLUMN a_asset_spread_type_OLD;

ALTER TABLE A_Asset_Change RENAME COLUMN a_depreciation_calc_type TO a_depreciation_calc_type_OLD;
ALTER TABLE A_Asset_Change ADD COLUMN a_depreciation_calc_type  NUMERIC(10);
UPDATE A_Asset_Change SET a_depreciation_calc_type=a_depreciation_calc_type_OLD::NUMERIC(10);
COMMIT;
ALTER TABLE A_Asset_Change DROP COLUMN a_depreciation_calc_type_OLD;

ALTER TABLE A_Asset_Change RENAME COLUMN a_disposal_loss TO a_disposal_loss_OLD;
ALTER TABLE A_Asset_Change ADD COLUMN a_disposal_loss  NUMERIC(10);
UPDATE A_Asset_Change SET a_disposal_loss=a_disposal_loss_OLD::NUMERIC(10);
COMMIT;
ALTER TABLE A_Asset_Change DROP COLUMN a_disposal_loss_OLD;

ALTER TABLE A_Asset_Change RENAME COLUMN a_disposal_revenue TO a_disposal_revenue_OLD;
ALTER TABLE A_Asset_Change ADD COLUMN a_disposal_revenue  NUMERIC(10);
UPDATE A_Asset_Change SET a_disposal_revenue=a_disposal_revenue_OLD::NUMERIC(10);
COMMIT;
ALTER TABLE A_Asset_Change DROP COLUMN a_disposal_revenue_OLD;

ALTER TABLE A_Asset_Change RENAME COLUMN conventiontype TO conventiontype_OLD;
ALTER TABLE A_Asset_Change ADD COLUMN conventiontype  NUMERIC(10);
UPDATE A_Asset_Change SET conventiontype=conventiontype_OLD::NUMERIC(10);
COMMIT;
ALTER TABLE A_Asset_Change DROP COLUMN conventiontype_OLD;

ALTER TABLE A_Asset_Change RENAME COLUMN depreciationtype TO depreciationtype_OLD;
ALTER TABLE A_Asset_Change ADD COLUMN depreciationtype  NUMERIC(10);
UPDATE A_Asset_Change SET depreciationtype=depreciationtype_OLD::NUMERIC(10);
COMMIT;
ALTER TABLE A_Asset_Change DROP COLUMN depreciationtype_OLD;

ALTER TABLE A_Asset_Group_Acct RENAME COLUMN a_asset_spread_type TO a_asset_spread_type_OLD;
ALTER TABLE A_Asset_Group_Acct ADD COLUMN a_asset_spread_type  NUMERIC(10);
UPDATE A_Asset_Group_Acct SET a_asset_spread_type=a_asset_spread_type_OLD::NUMERIC(10);
COMMIT;
ALTER TABLE A_Asset_Group_Acct DROP COLUMN a_asset_spread_type_OLD;

ALTER TABLE A_Asset_Group_Acct RENAME COLUMN A_Depreciation_Acct TO A_Depreciation_Acct_OLD;
ALTER TABLE A_Asset_Group_Acct ADD COLUMN A_Depreciation_Acct  NUMERIC(10);
UPDATE A_Asset_Group_Acct A_Depreciation_Acct SET A_Depreciation_Acct=A_Depreciation_Acct_OLD::NUMERIC(10);
COMMIT;
ALTER TABLE A_Asset_Group_Acct DROP COLUMN A_Depreciation_Acct_OLD;

ALTER TABLE A_Asset_Group_Acct RENAME COLUMN A_Depreciation_Calc_Type TO A_Depreciation_Calc_Type_OLD;
ALTER TABLE A_Asset_Group_Acct ADD COLUMN  A_Depreciation_Calc_Type NUMERIC(10);
UPDATE A_Asset_Group_Acct SET A_Depreciation_Calc_Type=A_Depreciation_Calc_Type_OLD::NUMERIC(10);
COMMIT;
ALTER TABLE A_Asset_Group_Acct DROP COLUMN A_Depreciation_Calc_Type_OLD;

ALTER TABLE A_Asset_Group_Acct RENAME COLUMN A_Disposal_Gain TO A_Disposal_Gain_OLD;
ALTER TABLE A_Asset_Group_Acct ADD COLUMN A_Disposal_Gain  NUMERIC(10);
UPDATE A_Asset_Group_Acct SET A_Disposal_Gain=A_Disposal_Gain_OLD::NUMERIC(10);
COMMIT;
ALTER TABLE A_Asset_Group_Acct DROP COLUMN A_Disposal_Gain_OLD;

ALTER TABLE A_Asset_Group_Acct RENAME COLUMN A_Disposal_Loss TO A_Disposal_Loss_OLD;
ALTER TABLE A_Asset_Group_Acct ADD COLUMN A_Disposal_Loss  NUMERIC(10);
UPDATE A_Asset_Group_Acct SET A_Disposal_Loss=A_Disposal_Loss_OLD::NUMERIC(10);
COMMIT;
ALTER TABLE A_Asset_Group_Acct DROP COLUMN A_Disposal_Loss_OLD;

ALTER TABLE A_Asset_Group_Acct RENAME COLUMN A_Disposal_Revenue TO A_Disposal_Revenue_OLD;
ALTER TABLE A_Asset_Group_Acct ADD COLUMN A_Disposal_Revenue  NUMERIC(10);
UPDATE A_Asset_Group_Acct SET A_Disposal_Revenue=A_Disposal_Revenue_OLD::NUMERIC(10);
COMMIT;
ALTER TABLE A_Asset_Group_Acct DROP COLUMN A_Disposal_Revenue_OLD;

ALTER TABLE A_Asset_Group_Acct RENAME COLUMN A_Reval_Accumdep_Offset_Cur TO A_Reval_Accumdep_Offset_Cur_OLD;
ALTER TABLE A_Asset_Group_Acct ADD COLUMN  A_Reval_Accumdep_Offset_Cur NUMERIC(10);
UPDATE A_Asset_Group_Acct SET A_Reval_Accumdep_Offset_Cur=A_Reval_Accumdep_Offset_Cur_OLD::NUMERIC(10);
COMMIT;
ALTER TABLE A_Asset_Group_Acct DROP COLUMN A_Reval_Accumdep_Offset_Cur_OLD;

ALTER TABLE A_Asset_Group_Acct RENAME COLUMN A_Reval_Accumdep_Offset_Prior TO A_Reval_Accumdep_Offset_Prior_OLD;
ALTER TABLE A_Asset_Group_Acct ADD COLUMN A_Reval_Accumdep_Offset_Prior  NUMERIC(10);
UPDATE A_Asset_Group_Acct SET A_Reval_Accumdep_Offset_Prior=A_Reval_Accumdep_Offset_Prior_OLD::NUMERIC(10);
COMMIT;
ALTER TABLE A_Asset_Group_Acct DROP COLUMN A_Reval_Accumdep_Offset_Prior_OLD;

ALTER TABLE A_Asset_Group_Acct RENAME COLUMN A_Reval_Cost_Offset TO A_Reval_Cost_Offset_OLD;
ALTER TABLE A_Asset_Group_Acct ADD COLUMN A_Reval_Cost_Offset  NUMERIC(10);
UPDATE A_Asset_Group_Acct SET A_Reval_Cost_Offset=A_Reval_Cost_Offset_OLD::NUMERIC(10);
COMMIT;
ALTER TABLE A_Asset_Group_Acct DROP COLUMN A_Reval_Cost_Offset_OLD;

ALTER TABLE A_Asset_Group_Acct RENAME COLUMN A_Reval_Cost_Offset_Prior TO A_Reval_Cost_Offset_Prior_OLD;
ALTER TABLE A_Asset_Group_Acct ADD COLUMN A_Reval_Cost_Offset_Prior  NUMERIC(10);
UPDATE A_Asset_Group_Acct SET A_Reval_Cost_Offset_Prior=A_Reval_Cost_Offset_Prior_OLD::NUMERIC(10);
COMMIT;
ALTER TABLE A_Asset_Group_Acct DROP COLUMN A_Reval_Cost_Offset_Prior_OLD;

ALTER TABLE A_Asset_Group_Acct RENAME COLUMN A_Reval_Depexp_Offset TO A_Reval_Depexp_Offset_OLD;
ALTER TABLE A_Asset_Group_Acct ADD COLUMN  A_Reval_Depexp_Offset NUMERIC(10);
UPDATE A_Asset_Group_Acct SET A_Reval_Depexp_Offset=A_Reval_Depexp_Offset_OLD::NUMERIC(10);
COMMIT;
ALTER TABLE A_Asset_Group_Acct DROP COLUMN A_Reval_Depexp_Offset_OLD;

ALTER TABLE A_Asset_Group_Acct RENAME COLUMN ConventionType TO ConventionType_OLD;
ALTER TABLE A_Asset_Group_Acct ADD COLUMN ConventionType  NUMERIC(10);
UPDATE A_Asset_Group_Acct SET ConventionType=ConventionType_OLD::NUMERIC(10);
COMMIT;
ALTER TABLE A_Asset_Group_Acct DROP COLUMN ConventionType_OLD;

ALTER TABLE A_Asset_Group_Acct RENAME COLUMN DepreciationType TO DepreciationType_OLD;
ALTER TABLE A_Asset_Group_Acct ADD COLUMN DepreciationType  NUMERIC(10);
UPDATE A_Asset_Group_Acct SET DepreciationType=DepreciationType_OLD::NUMERIC(10);
COMMIT;
ALTER TABLE A_Asset_Group_Acct DROP COLUMN DepreciationType_OLD;
-------------------
ALTER TABLE A_Asset_Split RENAME COLUMN A_Asset_ID_To TO A_Asset_ID_To_OLD;
ALTER TABLE A_Asset_Split ADD COLUMN A_Asset_ID_To  NUMERIC(10);
UPDATE A_Asset_Split SET A_Asset_ID_To=A_Asset_ID_To_OLD::NUMERIC(10);
COMMIT;
ALTER TABLE A_Asset_Split DROP COLUMN A_Asset_ID_To_OLD;

ALTER TABLE A_Asset_Transfer RENAME COLUMN A_Accumdepreciation_Acct_New TO A_Accumdepreciation_Acct_New_OLD;
ALTER TABLE A_Asset_Transfer ADD COLUMN A_Accumdepreciation_Acct_New  NUMERIC(10);
UPDATE A_Asset_Transfer SET A_Accumdepreciation_Acct_New=A_Accumdepreciation_Acct_New_OLD::NUMERIC(10);
COMMIT;
ALTER TABLE A_Asset_Transfer DROP COLUMN A_Accumdepreciation_Acct_New_OLD;

ALTER TABLE A_Asset_Transfer RENAME COLUMN A_Asset_Acct_New TO A_Asset_Acct_New_OLD;
ALTER TABLE A_Asset_Transfer ADD COLUMN A_Asset_Acct_New  NUMERIC(10);
UPDATE A_Asset_Transfer SET A_Asset_Acct_New=A_Asset_Acct_New_OLD::NUMERIC(10);
COMMIT;
ALTER TABLE A_Asset_Transfer DROP COLUMN A_Asset_Acct_New_OLD;

ALTER TABLE A_Asset_Transfer RENAME COLUMN A_Depreciation_Acct_New TO A_Depreciation_Acct_New_OLD;
ALTER TABLE A_Asset_Transfer ADD COLUMN  A_Depreciation_Acct_New NUMERIC(10);
UPDATE A_Asset_Transfer SET A_Depreciation_Acct_New=A_Depreciation_Acct_New_OLD::NUMERIC(10);
COMMIT;
ALTER TABLE A_Asset_Transfer DROP COLUMN A_Depreciation_Acct_New_OLD;

ALTER TABLE A_Asset_Transfer RENAME COLUMN A_Disposal_Loss_New TO A_Disposal_Loss_New_OLD;
ALTER TABLE A_Asset_Transfer ADD COLUMN  A_Disposal_Loss_New NUMERIC(10);
UPDATE A_Asset_Transfer SET A_Disposal_Loss_New=A_Disposal_Loss_New_OLD::NUMERIC(10);
COMMIT;
ALTER TABLE A_Asset_Transfer DROP COLUMN A_Disposal_Loss_New_OLD;

ALTER TABLE A_Asset_Transfer RENAME COLUMN A_Disposal_Revenue_New TO A_Disposal_Revenue_New_OLD;
ALTER TABLE A_Asset_Transfer ADD COLUMN A_Disposal_Revenue_New  NUMERIC(10);
UPDATE A_Asset_Transfer SET A_Disposal_Revenue_New=A_Disposal_Revenue_New_OLD::NUMERIC(10);
COMMIT;
ALTER TABLE A_Asset_Transfer DROP COLUMN A_Disposal_Revenue_New_OLD;

ALTER TABLE A_Depreciation_Exp RENAME COLUMN A_Account_Number TO A_Account_Number_OLD;
ALTER TABLE A_Depreciation_Exp ADD COLUMN A_Account_Number  NUMERIC(10);
UPDATE A_Depreciation_Exp SET A_Account_Number=A_Account_Number_OLD::NUMERIC(10);
COMMIT;
ALTER TABLE A_Depreciation_Exp DROP COLUMN A_Account_Number_OLD;

