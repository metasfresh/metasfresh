DROP VIEW RV_Asset_SumMonth
;

CREATE OR REPLACE VIEW RV_Asset_SumMonth AS
SELECT a.ad_client_id,
       a.ad_org_id,
       a.isactive,
       a.created,
       a.createdby,
       a.updated,
       a.updatedby,
       a.a_asset_id,
       a.a_asset_group_id,
       a.m_product_id,
       a.value,
       a.name,
       a.description,
       a.help,
       a.assetservicedate,
       a.c_bpartner_id,
       a.ad_user_id,
       a.versionno,
       firstof(ad.movementdate, 'MM'::CHARACTER VARYING) AS movementdate,
       COUNT(*)                                            AS deliverycount
FROM a_asset a
         JOIN a_asset_delivery ad ON a.a_asset_id = ad.a_asset_id
GROUP BY a.ad_client_id, a.ad_org_id, a.isactive, a.created, a.createdby, a.updated, a.updatedby, a.a_asset_id, a.a_asset_group_id, a.m_product_id, a.value, a.name, a.description, a.help, a.assetservicedate, a.c_bpartner_id, a.ad_user_id, a.versionno, (firstof(ad.movementdate, 'MM'::CHARACTER VARYING))
;