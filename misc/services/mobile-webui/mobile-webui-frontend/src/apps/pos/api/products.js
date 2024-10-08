import axios from 'axios';
import { apiBasePath } from '../../../constants';
import { unboxAxiosResponse } from '../../../utils';
import { useEffect, useState } from 'react';
import { toUrl } from '../../../utils/index';
import { useDebounce } from '../../../hooks/useDebounce';

const queryDebounceMillis = 300;

export const useProducts = ({ posTerminalId, onBarcodeResult }) => {
  const [isLoading, setLoading] = useState(false);
  const [queryString, setQueryStringState] = useState('');
  const [queryStringToSearch, setQueryStringToSearch] = useState('');
  const [products, setProducts] = useState({});

  const setQueryStringToSearchDebounced = useDebounce(setQueryStringToSearch, queryDebounceMillis);
  const setQueryString = (queryStringNew) => {
    setQueryStringState(queryStringNew);
    setQueryStringToSearchDebounced(queryStringNew);
  };

  useEffect(() => {
    let isMounted = true;
    setLoading(true);
    getProducts({ posTerminalId, query: queryStringToSearch })
      .then((productsNew) => {
        if (!isMounted) {
          console.log('ignoring getProducts result since the component is no longer mounted', { productsNew });
          return productsNew;
        }

        if (onBarcodeResult && productsNew.barcodeMatched && productsNew.list?.length === 1) {
          const product = productsNew.list[0];
          onBarcodeResult({
            ...product,
            scannedBarcode: queryStringToSearch,
          });
          setQueryString('');
        } else {
          setProducts(productsNew);
        }
      })
      .finally(() => {
        if (!isMounted) return;
        setLoading(false);
      });

    return () => {
      isMounted = false;
    };
  }, [posTerminalId, queryStringToSearch]);

  return {
    isLoading,
    isBarcodeMatched: !!products?.barcodeMatched,
    list: products?.list ?? [],
    queryString,
    setQueryString,
  };
};

const getProducts = ({ posTerminalId, query }) => {
  return axios
    .get(toUrl(`${apiBasePath}/pos/products`, { posTerminalId, query }))
    .then((response) => unboxAxiosResponse(response));
};
