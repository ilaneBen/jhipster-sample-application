import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './vendeur.reducer';

export const Vendeur = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const vendeurList = useAppSelector(state => state.vendeur.entities);
  const loading = useAppSelector(state => state.vendeur.loading);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        sort: `${sortState.sort},${sortState.order}`,
      }),
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?sort=${sortState.sort},${sortState.order}`;
    if (pageLocation.search !== endURL) {
      navigate(`${pageLocation.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [sortState.order, sortState.sort]);

  const sort = p => () => {
    setSortState({
      ...sortState,
      order: sortState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handleSyncList = () => {
    sortEntities();
  };

  const getSortIconByFieldName = (fieldName: string) => {
    const sortFieldName = sortState.sort;
    const order = sortState.order;
    if (sortFieldName !== fieldName) {
      return faSort;
    } else {
      return order === ASC ? faSortUp : faSortDown;
    }
  };

  return (
    <div>
      <h2 id="vendeur-heading" data-cy="VendeurHeading">
        <Translate contentKey="jhipsterSampleApplicationApp.vendeur.home.title">Vendeurs</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="jhipsterSampleApplicationApp.vendeur.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/vendeur/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="jhipsterSampleApplicationApp.vendeur.home.createLabel">Create new Vendeur</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {vendeurList && vendeurList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="jhipsterSampleApplicationApp.vendeur.id">ID</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('nom')}>
                  <Translate contentKey="jhipsterSampleApplicationApp.vendeur.nom">Nom</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('nom')} />
                </th>
                <th className="hand" onClick={sort('nbrVendu')}>
                  <Translate contentKey="jhipsterSampleApplicationApp.vendeur.nbrVendu">Nbr Vendu</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('nbrVendu')} />
                </th>
                <th className="hand" onClick={sort('objectifAtteint')}>
                  <Translate contentKey="jhipsterSampleApplicationApp.vendeur.objectifAtteint">Objectif Atteint</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('objectifAtteint')} />
                </th>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.vendeur.prime">Prime</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.vendeur.produits">Produits</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {vendeurList.map((vendeur, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/vendeur/${vendeur.id}`} color="link" size="sm">
                      {vendeur.id}
                    </Button>
                  </td>
                  <td>{vendeur.nom}</td>
                  <td>{vendeur.nbrVendu}</td>
                  <td>{vendeur.objectifAtteint ? 'true' : 'false'}</td>
                  <td>
                    {vendeur.primes
                      ? vendeur.primes.map((val, j) => (
                          <span key={j}>
                            <Link to={`/prime/${val.id}`}>{val.id}</Link>
                            {j === vendeur.primes.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td>{vendeur.produits ? <Link to={`/produits/${vendeur.produits.id}`}>{vendeur.produits.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/vendeur/${vendeur.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/vendeur/${vendeur.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        onClick={() => (location.href = `/vendeur/${vendeur.id}/delete`)}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="jhipsterSampleApplicationApp.vendeur.home.notFound">No Vendeurs found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Vendeur;
