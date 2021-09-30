import React, { useState, useEffect, useRef } from 'react';
import {
  BrowserMultiFormatOneDReader,
  // BrowserMultiFormatReader,
} from '@zxing/browser';

const getQRCodeReaderControls = async (selectedDeviceId) => {
  const codeReader = new BrowserMultiFormatOneDReader();

  const previewElem = document.querySelector('video');
  const videoElem = previewElem;

  // you can use the controls to stop() the scan or switchTorch() if available

  // decodeOnceFromVideoDevice
  const controls = await codeReader.decodeFromVideoDevice(selectedDeviceId, videoElem, (result, error, controls) => {
    // use the result and error values to choose your actions
    // you can also use controls API in this scope like the controls
    // returned from the method.
    console.log('---- result: ', result);
    console.log('---- error: ', error);
    console.log('---- controls: ', controls);

    if (result) {
      alert(JSON.stringify(result));
    }
  });

  return controls;
};

const ScanBarcode = () => {
  const controlsRef = useRef(null);
  const [selectedDeviceId, setSelectedDeviceId] = useState('');
  const [devices, setDevices] = useState([]);

  //   const handleChange = (event) => {
  //     setFile(URL.createObjectURL(event.target.files[0]));
  //   };

  useEffect(() => {
    const getDevices = async () => {
      const videoInputDevices = await BrowserMultiFormatOneDReader.listVideoInputDevices();

      // choose your media device (webcam, frontal camera, back camera, etc.)
      const selectedDeviceId = videoInputDevices[0].deviceId;

      console.log(`Started decode from camera with id ${selectedDeviceId}`);

      setDevices(videoInputDevices);
      setSelectedDeviceId(selectedDeviceId);
    };

    getDevices();
  }, []);

  return (
    <div>
      <div id="sourceSelectPanel">
        <label htmlFor="sourceSelect">Select the camera:</label>
        <select
          id="sourceSelect"
          value={selectedDeviceId}
          onChange={(event) => setSelectedDeviceId(event.target.value)}
        >
          {devices.map(({ deviceId, label }) => (
            <option key={deviceId} value={deviceId}>
              {label}
            </option>
          ))}
        </select>
        <br></br>
        (if you change the selected camera, please click again the Start button)
      </div>
      <button
        onClick={async () => {
          controlsRef.current = await getQRCodeReaderControls(selectedDeviceId);
        }}
      >
        Start
      </button>
      <button
        onClick={() => {
          controlsRef.current?.stop();
        }}
      >
        Stop
      </button>
      <br></br>
      <br></br>
      <br></br>
      <br></br>
    </div>
  );
};

const CodeScanner = () => {
  return (
    <div>
      <video id="video" width="600" height="400" style={{ border: '1px solid gray' }}></video>
      <ScanBarcode />
    </div>
  );
};

export default CodeScanner;
