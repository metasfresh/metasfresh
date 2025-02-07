import { useEffect, useState } from 'react';

let nextQueryId = 1;

export const useQuery = ({ queryFn, queryKey = [], enabled = true, onSuccess }) => {
  const [isPending, setIsPending] = useState(false);
  const [isSuccess, setIsSuccess] = useState(false);
  const [data, setData] = useState();

  useEffect(() => {
    const doLog = window.logUseQuery;

    // do nothing if not enabled
    if (!enabled) {
      if (doLog) console.log(`useQuery - do nothing because not enabled`, { queryKey, queryFn });
      return;
    }

    const queryId = doLog ? nextQueryId++ : null;
    let interrupted = false;

    //
    // Load data
    if (doLog) console.log(`useQuery(${queryId}) - started`, { queryKey, queryFn });
    setIsPending(true);
    queryFn()
      .then((data) => {
        if (interrupted) {
          if (doLog) console.log(`useQuery(${queryId}) - then - interrupted`, { data, queryKey, queryFn });
          return data;
        }

        setData(data);
        setIsSuccess(true);
        if (doLog) console.log(`useQuery(${queryId}) - success`, { data, queryKey, queryFn });
      })
      .finally(() => {
        if (interrupted) {
          if (doLog) console.log(`useQuery(${queryId}) - finally - interrupted`, { queryKey, queryFn });
          return;
        }

        setIsPending(false);
        if (doLog) console.log(`useQuery(${queryId}) - finally`, { queryKey, queryFn });
      });

    return () => {
      interrupted = true;
      setIsPending(false);
      if (doLog) console.log(`useQuery(${queryId}) - stopped`, { queryKey, queryFn });
    };
  }, [enabled, ...queryKey]);

  //
  // fire onSuccess
  useEffect(() => {
    if (onSuccess && isSuccess) {
      onSuccess(data);
    }
  }, [isSuccess, data]);

  //
  //
  return { isPending, data };
};
