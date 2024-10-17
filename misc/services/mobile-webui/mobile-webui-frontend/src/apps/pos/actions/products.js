import { useEffect, useState } from 'react';
import { useDebounce } from '../../../hooks/useDebounce';
import * as productsAPI from '../api/products';
import { usePOSTerminal } from './posTerminal';
import { TOGGLE_PRODUCT_CATEGORY_FILTER } from '../actionTypes';
import { useDispatch } from 'react-redux';

const queryDebounceMillis = 300;

export const useProducts = ({ onBarcodeResult }) => {
  const dispatch = useDispatch();
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
  const categories = posTerminal.productCategories ?? [];

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
    list: applyProductFilters({ productsArray: products?.list ?? [], categories }),
    queryString,
    setQueryString,

    categories,
    toggleCategorySelected: (categoryId) => dispatch(toggleProductCategoryFilterAction({ categoryId })),
  };
};

const applyProductFilters = ({ productsArray, categories }) => {
  // If list is null/empty then return the array as is, nothing to filter
  if (!productsArray || !productsArray?.length) return [];

  const selectedCategoryIds = categories.filter((category) => category.selected).map((category) => category.id);

  // if no categories selected then don't apply filtering
  if (selectedCategoryIds.length === 0) return productsArray;

  return productsArray.filter((product) => isProductMatchingSelectedCategoryIds({ product, selectedCategoryIds }));
};

const isProductMatchingSelectedCategoryIds = ({ product, selectedCategoryIds }) => {
  // if no categories selected then any product shall match
  if (!selectedCategoryIds?.length) return true;

  // if the product has no categories then for sure won't match our selected categories
  if (!product.categoryIds?.length) return false;

  return selectedCategoryIds.every((selectedCategoryId) => product.categoryIds.includes(selectedCategoryId));
};

const toggleProductCategoryFilterAction = ({ categoryId }) => {
  return {
    type: TOGGLE_PRODUCT_CATEGORY_FILTER,
    payload: { categoryId },
  };
};
