import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './produits.reducer';

export const ProduitsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const produitsEntity = useAppSelector(state => state.produits.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="produitsDetailsHeading">
          <Translate contentKey="jhipsterSampleApplicationApp.produits.detail.title">Produits</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{produitsEntity.id}</dd>
          <dt>
            <span id="nom">
              <Translate contentKey="jhipsterSampleApplicationApp.produits.nom">Nom</Translate>
            </span>
          </dt>
          <dd>{produitsEntity.nom}</dd>
          <dt>
            <span id="prix">
              <Translate contentKey="jhipsterSampleApplicationApp.produits.prix">Prix</Translate>
            </span>
          </dt>
          <dd>{produitsEntity.prix}</dd>
          <dt>
            <span id="photo">
              <Translate contentKey="jhipsterSampleApplicationApp.produits.photo">Photo</Translate>
            </span>
          </dt>
          <dd>{produitsEntity.photo}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="jhipsterSampleApplicationApp.produits.description">Description</Translate>
            </span>
          </dt>
          <dd>{produitsEntity.description}</dd>
          <dt>
            <Translate contentKey="jhipsterSampleApplicationApp.produits.magasin">Magasin</Translate>
          </dt>
          <dd>{produitsEntity.magasin ? produitsEntity.magasin.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/produits" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/produits/${produitsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ProduitsDetail;
