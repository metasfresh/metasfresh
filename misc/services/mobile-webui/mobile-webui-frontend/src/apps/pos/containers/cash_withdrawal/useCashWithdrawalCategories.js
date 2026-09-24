import { useEffect, useState } from 'react';
import { getCashWithdrawalCategories } from '../../api/posJournal';
import { toastError } from '../../../../utils/toast';

/**
 * @returns the cash withdrawal categories offered at the terminal (`[{ chargeId, name }]`), empty while loading or when none are configured
 */
export const useCashWithdrawalCategories = ({ posTerminalId, enabled = true }) => {
  const [categories, setCategories] = useState([]);

  useEffect(() => {
    if (!posTerminalId || !enabled) {
      setCategories([]);
      return;
    }

    let isCancelled = false;
    getCashWithdrawalCategories({ posTerminalId })
      .then((loadedCategories) => {
        if (!isCancelled) {
          setCategories(loadedCategories ?? []);
        }
      })
      .catch((axiosError) => toastError({ axiosError }));

    return () => {
      isCancelled = true;
    };
  }, [posTerminalId, enabled]);

  return categories;
};
