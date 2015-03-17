--discount
drop table if exists discount cascade;
create table discount
(
  id bigint not null,
  product_id bigint,
  discount_percent integer,
  discount_date timestamp without time zone,
  constraint pk_discount_pkey primary key (id),

  constraint fk_discount_product foreign key (product_id)
    references product(id)
    on update cascade on delete cascade
);

--seq_discount
drop sequence if exists seq_discount;
create sequence seq_discount;

create index idx_fk_discount_product
  on discount
  using btree
  (product_id);
