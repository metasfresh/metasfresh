import React, { useEffect, useContext } from 'react';
import { observer } from 'mobx-react';
import View from './View';
import { RootStoreContext } from '../models/Store';

const Info: React.FC = observer(() => {
  const store = useContext(RootStoreContext);

  useEffect(() => {
    store.info.fetchInfo();
  }, [store]);

  return (
    <View>
      <div>
        <h1 className="title p-4">Info/News</h1>
        <div className="mt-1 p-4">
          <p className="subtitle preserve-new-lines" dangerouslySetInnerHTML={{ __html: store.info.getContent }}></p>
        </div>
      </div>
    </View>
  );
});

export default Info;
