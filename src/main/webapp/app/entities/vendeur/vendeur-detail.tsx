import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './vendeur.reducer';

export const VendeurDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const vendeurEntity = useAppSelector(state => state.vendeur.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="vendeurDetailsHeading">
          <Translate contentKey="jhipsterSampleApplicationApp.vendeur.detail.title">Vendeur</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{vendeurEntity.id}</dd>
          <dt>
            <span id="nom">
              <Translate contentKey="jhipsterSampleApplicationApp.vendeur.nom">Nom</Translate>
            </span>
          </dt>
          <dd>{vendeurEntity.nom}</dd>
          <dt>
            <span id="nbrVendu">
              <Translate contentKey="jhipsterSampleApplicationApp.vendeur.nbrVendu">Nbr Vendu</Translate>
            </span>
          </dt>
          <dd>{vendeurEntity.nbrVendu}</dd>
          <dt>
            <span id="objectifAtteint">
              <Translate contentKey="jhipsterSampleApplicationApp.vendeur.objectifAtteint">Objectif Atteint</Translate>
            </span>
          </dt>
          <dd>{vendeurEntity.objectifAtteint ? 'true' : 'false'}</dd>
          <dt>
            <Translate contentKey="jhipsterSampleApplicationApp.vendeur.prime">Prime</Translate>
          </dt>
          <dd>
            {vendeurEntity.primes
              ? vendeurEntity.primes.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {vendeurEntity.primes && i === vendeurEntity.primes.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>
            <Translate contentKey="jhipsterSampleApplicationApp.vendeur.produits">Produits</Translate>
          </dt>
          <dd>{vendeurEntity.produits ? vendeurEntity.produits.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/vendeur" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/vendeur/${vendeurEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default VendeurDetail;
