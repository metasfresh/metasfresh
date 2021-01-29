import React, { ReactElement } from 'react';
import { useHistory } from 'react-router-dom';
import { translate } from '../utils/translate';

interface Props {
  id: string;
  name: string;
  dateStart: string;
  dateEnd: string;
  quantityPromised: string;
}

const RfQ: React.FunctionComponent<Props> = ({
  id,
  name,
  dateStart,
  dateEnd,
  quantityPromised,
}: Props): ReactElement => {
  const history = useHistory();

  return (
    <div className="product">
      <div
        className="box"
        onClick={() =>
          history.push({
            pathname: `/rfq/${id}`,
            state: { text: translate('RfQsListView.caption') },
          })
        }
      >
        <div className="columns is-mobile">
          <div className="column is-8">
            <div className="columns">
              <div className="column is-size-4-mobile no-p">{name}</div>
              <div className="column is-size-7 no-p">
                {dateStart} - {dateEnd}
              </div>
            </div>
          </div>
          <div className="column is-4 no-p">
            <div className="columns is-mobile">
              <div className="column mt-2 is-size-2-mobile no-p">{quantityPromised}</div>
              <div className="column green-check is-hidden-mobile">
                <span>
                  <i className="fas fa-check"></i>
                </span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default RfQ;
