export class X500NameModel {
  private _cn: string;

  private _surname: string;

  private _givenname: string;

  private _o: string;

  private _ou: string;

  private _c: string;

  private _e: string;

  private _uid: string;


  constructor(cn: string, surname: string, givenname: string, o: string, ou: string, c: string, e: string, uid: string) {
    this._cn = cn;
    this._surname = surname;
    this._givenname = givenname;
    this._o = o;
    this._ou = ou;
    this._c = c;
    this._e = e;
    this._uid = uid;
  }

  get cn(): string {
    return this._cn;
  }

  set cn(value: string) {
    this._cn = value;
  }

  get surname(): string {
    return this._surname;
  }

  set surname(value: string) {
    this._surname = value;
  }

  get givenname(): string {
    return this._givenname;
  }

  set givenname(value: string) {
    this._givenname = value;
  }

  get o(): string {
    return this._o;
  }

  set o(value: string) {
    this._o = value;
  }

  get ou(): string {
    return this._ou;
  }

  set ou(value: string) {
    this._ou = value;
  }

  get c(): string {
    return this._c;
  }

  set c(value: string) {
    this._c = value;
  }

  get e(): string {
    return this._e;
  }

  set e(value: string) {
    this._e = value;
  }

  get uid(): string {
    return this._uid;
  }

  set uid(value: string) {
    this._uid = value;
  }
}
