import React, { useEffect, useContext } from 'react';
import { observer } from 'mobx-react';
import View from './View';
import { RootStoreContext } from '../models/Store';
import { translate } from '../utils/translate';

const Info: React.FunctionComponent = observer(() => {
  const store = useContext(RootStoreContext);

  useEffect(() => {
    store.info.fetchInfo();
    store.navigation.setViewNames(translate('InfoMessageView.caption'));
  }, [store]);

  /**
   * To preserve the html formatting and allow <B> <U> and <I> tags we used dangerouslySetInnerHTML
   * TODO: this can be further improved with usage of a sanitizer like https://github.com/cure53/DOMPurify
   */
  return (
    <View>
      <div>
        <div className="mt-1 p-4">
          <p className="subtitle preserve-new-lines" dangerouslySetInnerHTML={{ __html: store.info.getContent }}></p>
        </div>
      </div>
    </View>
  );
});

export default Info;
