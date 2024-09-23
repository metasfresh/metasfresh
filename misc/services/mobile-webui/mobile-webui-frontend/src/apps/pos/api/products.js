import axios from 'axios';
import { apiBasePath } from '../../../constants';
import { unboxAxiosResponse } from '../../../utils';
import { useEffect, useState } from 'react';

export const useProducts = () => {
  const [loading, setLoading] = useState(false);
  const [products, setProducts] = useState([]);

  useEffect(() => {
    setLoading(true);
    getProducts()
      .then(setProducts)
      .finally(() => setLoading(false));
  }, []);

  return {
    isProductsLoading: loading,
    products,
  };
};

const getProducts = () => {
  return axios
    .get(`${apiBasePath}/pos/products`)
    .then((response) => unboxAxiosResponse(response))
    .then((data) => data.list);
};
