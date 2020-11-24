DROP FUNCTION IF EXISTS report.isShowQRBill(IN p_AD_Client_ID numeric, IN p_AD_Org_ID numeric);

create or replace function report.isShowQRBill(IN p_AD_Client_ID numeric, IN p_AD_Org_ID numeric) RETURNS CHAR AS
$$
DECLARE
    isShowQRBill CHAR DEFAULT 'Y';
BEGIN
 
        SELECT VALUE
        INTO isShowQRBill
        from AD_SysConfig
        where Name = 'ShowQRBill' AND AD_Client_ID IN (0,1000000) AND AD_Org_ID IN (0, p_AD_Org_ID) ORDER BY AD_Org_ID DESC, AD_Client_ID DESC LIMIT 1;

    return isShowQRBill;
END
$$
    LANGUAGE plpgsql;