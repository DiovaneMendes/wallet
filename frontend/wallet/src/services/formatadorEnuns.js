const formataCapsLock = periodo => {
  const primeiraLetra = periodo.substring(0, 1)
  const restanteMinuscula = periodo.substring(1, periodo.length).toLocaleLowerCase()

  return primeiraLetra + restanteMinuscula
}

export default formataCapsLock