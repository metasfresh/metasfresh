import { useEffect, useState } from 'react';

let nextQueryId = 1;

export const useQuery = ({ queryFn, queryKey = [], enabled = true }) => {
  const [isPending, setIsPending] = useState(false);
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

    if (doLog) console.log(`useQuery(${queryId}) - started`, { queryKey, queryFn });
    setIsPending(true);
    queryFn()
      .then((data) => {
        if (interrupted) {
          if (doLog) console.log(`useQuery(${queryId}) - then - interrupted`, { data, queryKey, queryFn });
          return data;
        }

        setData(data);
        if (doLog) console.log(`useQuery(${queryId}) - then`, { data, queryKey, queryFn });
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

  return { isPending, data };
};
