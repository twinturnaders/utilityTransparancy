
-- Name: postgis; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS postgis WITH SCHEMA public;


--
-- Name: municipalities_set_zip_center(); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION public.municipalities_set_zip_center() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
    IF NEW.geo_bounds IS NOT NULL THEN
        NEW.zip_center := ST_PointOnSurface(NEW.geo_bounds);
    END IF;
    RETURN NEW;
END $$;


--
-- Name: bill_fees; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.bill_fees (
    id bigint NOT NULL,
    bill_id bigint,
    fee_type text,
    fee_amount numeric(38,2)
);


--
-- Name: bill_fees_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

ALTER TABLE public.bill_fees ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.bill_fees_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: crowd_fees; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.crowd_fees (
    id bigint NOT NULL,
    amount numeric(38,2),
    description character varying(50),
    submission_id bigint,
    name character varying(255)
);


--
-- Name: crowd_fees_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

ALTER TABLE public.crowd_fees ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.crowd_fees_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: crowd_rates; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.crowd_rates (
    id bigint NOT NULL,
    price_per_unit numeric(38,2),
    rate_min_range integer,
    rate_max_range integer,
    submission_id bigint,
    rate numeric(38,2),
    up_to integer
);


--
-- Name: crowd_rates_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

ALTER TABLE public.crowd_rates ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.crowd_rates_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: crowdsources; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.crowdsources (
    id bigint NOT NULL,
    user_id bigint,
    original_file_name text,
    stored_file_path text,
    town_name character varying(100),
    rate_type character varying(50),
    base_rate numeric(38,2),
    notes text,
    created_at date DEFAULT now(),
    submission_file_uploaded boolean,
    submission_status character varying(50),
    fixed_rate boolean,
    resolved_municipality_id bigint,
    updated_at timestamp(6) with time zone
);


--
-- Name: crowdsources_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

ALTER TABLE public.crowdsources ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.crowdsources_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: fees; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.fees (
    id bigint NOT NULL,
    municipality_id bigint,
    fee_type character varying(100),
    fee_amount numeric(38,2),
    fee_policy text,
    base_fee text,
    created_at date DEFAULT now() NOT NULL,
    end_date date,
    is_percentage boolean,
    percentage_amount numeric(38,2)
);


--
-- Name: fees_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

ALTER TABLE public.fees ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.fees_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: mt_inc_towns; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.mt_inc_towns (
    id bigint NOT NULL,
    town_name character varying(100),
    county character varying(100)
);


--
-- Name: mt_inc_towns_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

ALTER TABLE public.mt_inc_towns ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.mt_inc_towns_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: municipalities; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.municipalities (
    id bigint NOT NULL,
    name character varying(255) NOT NULL,
    county character varying(255),
    state character varying(255) DEFAULT 'MT'::bpchar,
    zip_center public.geometry(Point,4326),
    geo_bounds public.geometry(MultiPolygon,4326),
    confidence_rating character varying(5)
);


--
-- Name: municipalities_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

ALTER TABLE public.municipalities ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.municipalities_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: rate_variances; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.rate_variances (
    id bigint NOT NULL,
    municipality_id bigint,
    wflat_rate_range boolean,
    water_ppu numeric(38,2),
    wrange_min integer,
    wrange_max integer,
    created_at date DEFAULT now(),
    end_date date,
    priority integer DEFAULT 100 NOT NULL,
    measure_unit character varying(50),
    CONSTRAINT chk_priority_positive CHECK ((priority > 0))
);


--
-- Name: rate_variances_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

ALTER TABLE public.rate_variances ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.rate_variances_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: sewer_rate_variance; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.sewer_rate_variance (
    id bigint NOT NULL,
    municipality_id bigint,
    sewer_ppu numeric(38,2),
    created_at date DEFAULT now(),
    end_date date,
    measure_unit text
);


--
-- Name: sewer_rate_variance_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

ALTER TABLE public.sewer_rate_variance ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.sewer_rate_variance_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: sewer_rates; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.sewer_rates (
    id bigint NOT NULL,
    municipality_id bigint,
    base_rate numeric(38,2),
    fixed_rate boolean,
    create_date date DEFAULT now(),
    end_date date,
    base_gallons integer
);


--
-- Name: sewerrates_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

ALTER TABLE public.sewer_rates ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.sewerrates_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: user_bills; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.user_bills (
    id bigint NOT NULL,
    user_id bigint,
    sewer_use_amount numeric(38,2),
    water_use_amount numeric(38,2),
    sewer_charge numeric(38,2),
    water_charge numeric(38,2),
    bill_date date,
    due_date date,
    paid_date date,
    paid boolean,
    is_paid boolean
);


--
-- Name: userbills_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

ALTER TABLE public.user_bills ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.userbills_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: users; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.users (
    id bigint NOT NULL,
    municipality_id bigint,
    passwordhash character varying(150),
    email character varying(50),
    role character varying(20) DEFAULT 'USER'::character varying NOT NULL,
    create_date timestamp with time zone DEFAULT now()
);


--
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

ALTER TABLE public.users ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: water_rates; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.water_rates (
    id bigint NOT NULL,
    municipality_id bigint,
    base_rate numeric(38,2),
    fixed_rate boolean,
    start_date date DEFAULT now(),
    end_date date,
    base_gallons integer,
    confidence_rating character varying(5)
);


--
-- Name: waterrates_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

ALTER TABLE public.water_rates ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.waterrates_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: zip_codes; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.zip_codes (
    zip_code character varying(10) NOT NULL,
    city_name character varying(100),
    county_name character varying(100),
    zip_center public.geometry(Point,4326),
    id bigint NOT NULL
);


--
-- Name: zip_codes_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.zip_codes_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: zip_codes_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.zip_codes_id_seq OWNED BY public.zip_codes.id;


--
-- Name: zip_codes id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.zip_codes ALTER COLUMN id SET DEFAULT nextval('public.zip_codes_id_seq'::regclass);


--
-- Name: bill_fees bill_fees_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.bill_fees
    ADD CONSTRAINT bill_fees_pkey PRIMARY KEY (id);


--
-- Name: crowd_fees crowd_fees_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.crowd_fees
    ADD CONSTRAINT crowd_fees_pkey PRIMARY KEY (id);


--
-- Name: crowd_rates crowd_rates_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.crowd_rates
    ADD CONSTRAINT crowd_rates_pkey PRIMARY KEY (id);


--
-- Name: crowdsources crowdsources_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.crowdsources
    ADD CONSTRAINT crowdsources_pkey PRIMARY KEY (id);


--
-- Name: fees fees_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.fees
    ADD CONSTRAINT fees_pkey PRIMARY KEY (id);


--
-- Name: mt_inc_towns mt_inc_towns_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.mt_inc_towns
    ADD CONSTRAINT mt_inc_towns_pkey PRIMARY KEY (id);


--
-- Name: municipalities municipalities_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.municipalities
    ADD CONSTRAINT municipalities_pkey PRIMARY KEY (id);


--
-- Name: rate_variances rate_variances_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.rate_variances
    ADD CONSTRAINT rate_variances_pkey PRIMARY KEY (id);


--
-- Name: sewer_rate_variance sewer_rate_variance_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.sewer_rate_variance
    ADD CONSTRAINT sewer_rate_variance_pkey PRIMARY KEY (id);


--
-- Name: sewer_rates sewerrates_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.sewer_rates
    ADD CONSTRAINT sewerrates_pkey PRIMARY KEY (id);


--
-- Name: user_bills userbills_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.user_bills
    ADD CONSTRAINT userbills_pkey PRIMARY KEY (id);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: water_rates waterrates_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.water_rates
    ADD CONSTRAINT waterrates_pkey PRIMARY KEY (id);


--
-- Name: zip_codes zip_codes_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.zip_codes
    ADD CONSTRAINT zip_codes_pkey PRIMARY KEY (zip_code);


--
-- Name: bill_user_muni_date_idx; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX bill_user_muni_date_idx ON public.user_bills USING btree (user_id, bill_date);


--
-- Name: municipalities_name_key; Type: INDEX; Schema: public; Owner: -
--

CREATE UNIQUE INDEX municipalities_name_key ON public.municipalities USING btree (name);


--
-- Name: municipalities_name_uq; Type: INDEX; Schema: public; Owner: -
--

CREATE UNIQUE INDEX municipalities_name_uq ON public.municipalities USING btree (regexp_replace(lower(btrim((name)::text)), '\s+'::text, ' '::text, 'g'::text));


--
-- Name: zips_geobounds_gix; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX zips_geobounds_gix ON public.municipalities USING gist (geo_bounds);


--
-- Name: municipalities trig_zip_center; Type: TRIGGER; Schema: public; Owner: -
--

CREATE TRIGGER trig_zip_center BEFORE INSERT OR UPDATE OF geo_bounds ON public.municipalities FOR EACH ROW EXECUTE FUNCTION public.municipalities_set_zip_center();


--
-- Name: bill_fees bill_fees_bill_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.bill_fees
    ADD CONSTRAINT bill_fees_bill_id_fkey FOREIGN KEY (bill_id) REFERENCES public.user_bills(id);


--
-- Name: crowd_fees crowd_fees_submission_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.crowd_fees
    ADD CONSTRAINT crowd_fees_submission_id_fkey FOREIGN KEY (submission_id) REFERENCES public.crowdsources(id);


--
-- Name: crowd_rates crowd_rates_submission_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.crowd_rates
    ADD CONSTRAINT crowd_rates_submission_id_fkey FOREIGN KEY (submission_id) REFERENCES public.crowdsources(id);


--
-- Name: crowdsources crowdsources_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.crowdsources
    ADD CONSTRAINT crowdsources_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- Name: fees fees_municipalityid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.fees
    ADD CONSTRAINT fees_municipalityid_fkey FOREIGN KEY (municipality_id) REFERENCES public.municipalities(id);


--
-- Name: users fk_municipalityid; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT fk_municipalityid FOREIGN KEY (municipality_id) REFERENCES public.municipalities(id);


--
-- Name: sewer_rate_variance fkxgl4yluing7wfgqoxnhckwpc; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.sewer_rate_variance
    ADD CONSTRAINT fkxgl4yluing7wfgqoxnhckwpc FOREIGN KEY (municipality_id) REFERENCES public.municipalities(id);


--
-- Name: rate_variances rate_variances_municipality_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.rate_variances
    ADD CONSTRAINT rate_variances_municipality_id_fkey FOREIGN KEY (municipality_id) REFERENCES public.municipalities(id);


--
-- Name: sewer_rates sewerrates_municipalityid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.sewer_rates
    ADD CONSTRAINT sewerrates_municipalityid_fkey FOREIGN KEY (municipality_id) REFERENCES public.municipalities(id);


--
-- Name: user_bills userbills_userid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.user_bills
    ADD CONSTRAINT userbills_userid_fkey FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- Name: water_rates waterrates_municipalityid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.water_rates
    ADD CONSTRAINT waterrates_municipalityid_fkey FOREIGN KEY (municipality_id) REFERENCES public.municipalities(id);


--
-- PostgreSQL database dump complete
--



