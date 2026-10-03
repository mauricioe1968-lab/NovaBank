--
-- PostgreSQL database dump
--

\restrict cRkpd9Ef7hHaYUNo5ra8DC8ObuFCqTWvH2GPdAfmJ6ttloEquZv0zs1LUWrYOcS

-- Dumped from database version 18.4
-- Dumped by pg_dump version 18.4

-- Started on 2026-10-02 22:41:08

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 5044 (class 1262 OID 16651)
-- Name: javinha; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE javinha WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Portuguese_Brazil.1252';


ALTER DATABASE javinha OWNER TO postgres;

\unrestrict cRkpd9Ef7hHaYUNo5ra8DC8ObuFCqTWvH2GPdAfmJ6ttloEquZv0zs1LUWrYOcS
\connect javinha
\restrict cRkpd9Ef7hHaYUNo5ra8DC8ObuFCqTWvH2GPdAfmJ6ttloEquZv0zs1LUWrYOcS

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 220 (class 1259 OID 16653)
-- Name: cliente; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.cliente (
    id_cliente integer NOT NULL,
    nome character varying(100) NOT NULL,
    tipo_cliente character(2) NOT NULL,
    cpf character varying(14),
    cnpj character varying(18),
    data_nascimento date,
    data_constituicao date
);


ALTER TABLE public.cliente OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 16652)
-- Name: cliente_id_cliente_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.cliente_id_cliente_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.cliente_id_cliente_seq OWNER TO postgres;

--
-- TOC entry 5045 (class 0 OID 0)
-- Dependencies: 219
-- Name: cliente_id_cliente_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.cliente_id_cliente_seq OWNED BY public.cliente.id_cliente;


--
-- TOC entry 225 (class 1259 OID 24843)
-- Name: conta_numero_conta_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.conta_numero_conta_seq
    START WITH 2100
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.conta_numero_conta_seq OWNER TO postgres;

--
-- TOC entry 222 (class 1259 OID 16663)
-- Name: conta; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.conta (
    id_conta integer NOT NULL,
    numero_conta integer DEFAULT nextval('public.conta_numero_conta_seq'::regclass) NOT NULL,
    agencia character varying(10) DEFAULT '001'::character varying NOT NULL,
    saldo numeric(15,2) DEFAULT 900.00 NOT NULL,
    id_cliente integer NOT NULL
);


ALTER TABLE public.conta OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 16662)
-- Name: conta_id_conta_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.conta_id_conta_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.conta_id_conta_seq OWNER TO postgres;

--
-- TOC entry 5046 (class 0 OID 0)
-- Dependencies: 221
-- Name: conta_id_conta_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.conta_id_conta_seq OWNED BY public.conta.id_conta;


--
-- TOC entry 224 (class 1259 OID 16685)
-- Name: transacao; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.transacao (
    id_transacao integer NOT NULL,
    conta_origem integer,
    conta_destino integer,
    tipo_operacao character varying(30) NOT NULL,
    valor numeric(15,2) NOT NULL,
    data_operacao timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE public.transacao OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 16684)
-- Name: transacao_id_transacao_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.transacao_id_transacao_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.transacao_id_transacao_seq OWNER TO postgres;

--
-- TOC entry 5047 (class 0 OID 0)
-- Dependencies: 223
-- Name: transacao_id_transacao_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.transacao_id_transacao_seq OWNED BY public.transacao.id_transacao;


--
-- TOC entry 4867 (class 2604 OID 16656)
-- Name: cliente id_cliente; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cliente ALTER COLUMN id_cliente SET DEFAULT nextval('public.cliente_id_cliente_seq'::regclass);


--
-- TOC entry 4868 (class 2604 OID 16666)
-- Name: conta id_conta; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.conta ALTER COLUMN id_conta SET DEFAULT nextval('public.conta_id_conta_seq'::regclass);


--
-- TOC entry 4872 (class 2604 OID 16688)
-- Name: transacao id_transacao; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.transacao ALTER COLUMN id_transacao SET DEFAULT nextval('public.transacao_id_transacao_seq'::regclass);


--
-- TOC entry 5033 (class 0 OID 16653)
-- Dependencies: 220
-- Data for Name: cliente; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.cliente VALUES (2, 'MAURICIO', 'PF', '11111111111', NULL, '1998-08-18', NULL);
INSERT INTO public.cliente VALUES (3, 'MAURICIO', 'PF', '12312313', NULL, '2000-10-09', NULL);
INSERT INTO public.cliente VALUES (4, 'Mauricio', 'PF', '12312312312', NULL, '2000-08-10', NULL);
INSERT INTO public.cliente VALUES (6, 'MW BRASIL SA', 'PJ', NULL, '123123123123243', NULL, '2000-01-01');
INSERT INTO public.cliente VALUES (1, 'Mauricio Eduardo', 'PF', '62173599918', NULL, '2000-08-01', NULL);
INSERT INTO public.cliente VALUES (7, 'João da Silva', 'PF', '12345678900', NULL, '1970-05-24', NULL);
INSERT INTO public.cliente VALUES (5, 'MW BRASIL S/A.', 'PJ', '12345678900', '123456780011441', '1980-08-15', '1999-10-01');


--
-- TOC entry 5035 (class 0 OID 16663)
-- Dependencies: 222
-- Data for Name: conta; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.conta VALUES (1, 3, '001', 999.00, 2);
INSERT INTO public.conta VALUES (2, 4, '001', 999.00, 3);
INSERT INTO public.conta VALUES (4, 2101, '001', 999.00, 5);
INSERT INTO public.conta VALUES (5, 2102, '001', 40999.00, 6);
INSERT INTO public.conta VALUES (6, 2103, '001', 999.00, 7);
INSERT INTO public.conta VALUES (3, 2100, '001', 1989.00, 4);


--
-- TOC entry 5037 (class 0 OID 16685)
-- Dependencies: 224
-- Data for Name: transacao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5048 (class 0 OID 0)
-- Dependencies: 219
-- Name: cliente_id_cliente_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.cliente_id_cliente_seq', 7, true);


--
-- TOC entry 5049 (class 0 OID 0)
-- Dependencies: 221
-- Name: conta_id_conta_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.conta_id_conta_seq', 6, true);


--
-- TOC entry 5050 (class 0 OID 0)
-- Dependencies: 225
-- Name: conta_numero_conta_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.conta_numero_conta_seq', 2103, true);


--
-- TOC entry 5051 (class 0 OID 0)
-- Dependencies: 223
-- Name: transacao_id_transacao_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.transacao_id_transacao_seq', 3, true);


--
-- TOC entry 4875 (class 2606 OID 16661)
-- Name: cliente cliente_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cliente
    ADD CONSTRAINT cliente_pkey PRIMARY KEY (id_cliente);


--
-- TOC entry 4877 (class 2606 OID 16677)
-- Name: conta conta_numero_conta_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.conta
    ADD CONSTRAINT conta_numero_conta_key UNIQUE (numero_conta);


--
-- TOC entry 4879 (class 2606 OID 16675)
-- Name: conta conta_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.conta
    ADD CONSTRAINT conta_pkey PRIMARY KEY (id_conta);


--
-- TOC entry 4881 (class 2606 OID 16694)
-- Name: transacao transacao_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.transacao
    ADD CONSTRAINT transacao_pkey PRIMARY KEY (id_transacao);


--
-- TOC entry 4882 (class 2606 OID 16678)
-- Name: conta fk_conta_cliente; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.conta
    ADD CONSTRAINT fk_conta_cliente FOREIGN KEY (id_cliente) REFERENCES public.cliente(id_cliente);


--
-- TOC entry 4883 (class 2606 OID 16700)
-- Name: transacao fk_transacao_destino; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.transacao
    ADD CONSTRAINT fk_transacao_destino FOREIGN KEY (conta_destino) REFERENCES public.conta(id_conta);


--
-- TOC entry 4884 (class 2606 OID 16695)
-- Name: transacao fk_transacao_origem; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.transacao
    ADD CONSTRAINT fk_transacao_origem FOREIGN KEY (conta_origem) REFERENCES public.conta(id_conta);


-- Completed on 2026-10-02 22:41:08

--
-- PostgreSQL database dump complete
--

\unrestrict cRkpd9Ef7hHaYUNo5ra8DC8ObuFCqTWvH2GPdAfmJ6ttloEquZv0zs1LUWrYOcS

