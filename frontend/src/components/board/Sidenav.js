import update from 'immutability-helper';
import React, { Component } from 'react';
import InfiniteScroll from 'react-infinite-scroller';
import onClickOutside from 'react-onclickoutside';
import PropTypes from 'prop-types';
import { createView, getLayout, getView } from '../../actions/BoardActions';
import Loader from '../app/Loader';
import Card from './Card';

class Sidenav extends Component {
  constructor(props) {
    super(props);

    this.state = {
      view: {},
      emptyText: '',
      emptyHint: '',
      loading: false,
    };
  }

  UNSAFE_componentWillMount = () => {
    const { boardId, viewId, setViewId } = this.props;

    this.setState({
      loading: true,
    });

    if (viewId) {
      getView(boardId, viewId, 0).then((res) =>
        this.setState({
          view: res.data,
          loading: false,
        })
      );
    } else {
      createView(boardId).then((res) => {
        setViewId(res.data.viewId);
        this.getBoardView({ boardId, viewId: res.data.viewId, firstRow: 0 });
      });
    }
  };

  /**
   * @method refreshView
   * @summary refreshes the view for the given boardId
   * @param {string} boardId
   */
  refreshView = (boardId) => {
    const { viewId } = this.state.view;
    this.getBoardView({ boardId, viewId, firstRow: 0 });
  };

  /**
   * @method getBoardView
   * @summary Fetches the view info for a given board
   * @param {object}
   */
  getBoardView = ({ boardId, viewId, firstRow }) => {
    getView(boardId, viewId, firstRow).then((res) =>
      this.setState({ view: res.data, loading: false })
    );
  };

  getCardIndex = (cardId) => {
    const { view } = this.state;
    let result = -1;

    if (view && view.result) {
      view.result.forEach((card, index) => {
        if (card && card.cardId === cardId) {
          result = index;
        }
      });
    }

    return result;
  };

  componentDidMount = () => {
    const { boardId } = this.props;
    getLayout(boardId).then((res) =>
      this.setState({
        emptyText: res.data.emptyResultText,
        emptyHint: res.data.emptyResultHint,
      })
    );
  };

  loadMore = (page) => {
    const { boardId, viewId } = this.props;

    this.setState({
      loading: true,
    });

    getView(boardId, viewId, page).then((res) =>
      this.setState((prev) =>
        update(prev, {
          view: {
            result: { $push: res.data.result },
          },
          loading: { $set: false },
        })
      )
    );
  };

  handleClickOutside = () => {
    const { onClickOutside } = this.props;
    onClickOutside();
  };

  render() {
    const { view, emptyText, emptyHint, loading } = this.state;

    return (
      <div className="board-sidenav overlay-shadow">
        <InfiniteScroll
          pageStart={0}
          loadMore={this.loadMore}
          initialLoad={false}
          loader={loading ? <Loader /> : <div />}
          hasMore={view.result && view.size >= view.result.length}
          useWindow={false}
        >
          <div className="board-sidenav-header">Add cards</div>
          <div>
            {view.result &&
              view.result.map((card, i) => (
                <Card key={i} index={i} {...card} />
              ))}
            {view.result && view.result.length === 0 && (
              <div className="empty-text">
                {emptyText}
                {emptyHint ? '. ' + emptyHint : ''}
              </div>
            )}
          </div>
        </InfiniteScroll>
      </div>
    );
  }
}

Sidenav.propTypes = {
  boardId: PropTypes.number,
  viewId: PropTypes.string,
  setViewId: PropTypes.func,
  onClickOutside: PropTypes.func,
};

export default onClickOutside(Sidenav);
