update m_matchinv set costamountinvoiced=costamount where type='C' and coalesce(costamountinvoiced,0)=0;
