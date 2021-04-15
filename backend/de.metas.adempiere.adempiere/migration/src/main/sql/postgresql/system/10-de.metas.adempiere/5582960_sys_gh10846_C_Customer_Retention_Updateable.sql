UPDATE AD_Column
SET IsUpdateable='Y', UpdatedBy=99, Updated='2021-03-18 15:12:54.620947 +01:00'
WHERE columnname = 'CustomerRetention'
  AND ad_table_id = get_table_id('C_Customer_Retention')
;
