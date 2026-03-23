import { useEffect, useRef, useState } from 'react';

/**
 * Custom hook that wraps the Web NFC API (NDEFReader).
 * Listens for NFC tag reads and returns the NDEF message.
 *
 * @returns {{ isNfcSupported: boolean, isScanning: boolean, lastRead: NDEFMessage|null, error: string|null }}
 */
export const useNfcReader = () => {
  const [isNfcSupported] = useState(() => typeof window !== 'undefined' && 'NDEFReader' in window);
  const [isScanning, setIsScanning] = useState(false);
  const [lastRead, setLastRead] = useState(null);
  const [error, setError] = useState(null);
  const abortControllerRef = useRef(null);

  useEffect(() => {
    if (!isNfcSupported) {
      return;
    }

    const startScanning = async () => {
      try {
        const ndef = new window.NDEFReader();
        abortControllerRef.current = new AbortController();

        await ndef.scan({ signal: abortControllerRef.current.signal });
        setIsScanning(true);
        setError(null);

        ndef.addEventListener('reading', (event) => {
          setLastRead(event.message);
        });

        ndef.addEventListener('readingerror', () => {
          setError('NFC read error');
        });
      } catch (err) {
        console.error('NFC scan failed:', err);
        setError(err.message || 'NFC scan failed');
        setIsScanning(false);
      }
    };

    startScanning();

    return () => {
      if (abortControllerRef.current) {
        abortControllerRef.current.abort();
        abortControllerRef.current = null;
      }
      setIsScanning(false);
    };
  }, [isNfcSupported]);

  return { isNfcSupported, isScanning, lastRead, error };
};
