update AD_Sequence
  set CurrentNext =CurrentNext +1
where
  Name='DocumentNo_M_Product'
and ad_client_id = 1000000
and CurrentNext=1000000;