update M_Product set M_CustomsTariff_id  = t.M_CustomsTariff_ID
from M_CustomsTariff t where t.value = M_Product.CustomsTariff;
