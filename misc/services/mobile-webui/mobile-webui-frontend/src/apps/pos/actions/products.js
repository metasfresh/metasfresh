import { useEffect, useState } from 'react';
import { useDebounce } from '../../../hooks/useDebounce';
import * as productsAPI from '../api/products';
import { usePOSTerminal } from './posTerminal';

const queryDebounceMillis = 300;

export const useProducts = ({ onBarcodeResult }) => {
  const posTerminal = usePOSTerminal();
  const [isLoading, setLoading] = useState(false);
  const [queryString, setQueryStringState] = useState('');
  const [queryStringToSearch, setQueryStringToSearch] = useState('');
  const [products, setProducts] = useState({});

  const setQueryStringToSearchDebounced = useDebounce(setQueryStringToSearch, queryDebounceMillis);
  const setQueryString = (queryStringNew) => {
    setQueryStringState(queryStringNew);
    setQueryStringToSearchDebounced(queryStringNew);
  };

  const posTerminalId = posTerminal.id;
  const posTerminalProductsArray = posTerminal.products ?? [];

  useEffect(() => {
    let isMounted = true;

    if (!posTerminalId) {
      setProducts({ list: [] });
    } else if (!queryStringToSearch) {
      setProducts({ list: posTerminalProductsArray });
    } else {
      setLoading(true);
      productsAPI
        .getProducts({ posTerminalId, query: queryStringToSearch })
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
    }

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
