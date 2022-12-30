package br.com.dio.desafio.dominio;

import java.util.*;

public class Dev implements Comparable<Dev>{
    private String nome;
    private Double totalXp;
    private String nomeBootcamp;
    private Set<Conteudo> conteudosInscritos = new LinkedHashSet<>();
    private Set<Conteudo> conteudosConcluidos = new LinkedHashSet<>();

    public void inscreverBootcamp(Bootcamp bootcamp){
        this.conteudosInscritos.addAll(bootcamp.getConteudos());
        this.setNomeBootcamp(bootcamp.getNome());
        bootcamp.getDevsInscritos().add(this);
    }

    public void progredir() {
        Optional<Conteudo> conteudo = this.conteudosInscritos.stream().findFirst();
        if(conteudo.isPresent()) {
            this.conteudosConcluidos.add(conteudo.get());
            this.conteudosInscritos.remove(conteudo.get());
            this.calcularTotalXp();
        } else {
            System.err.println("Você não está matriculado em nenhum conteúdo!");
        }
    }

    public void calcularTotalXp() {
        Iterator<Conteudo> iterator = this.conteudosConcluidos.iterator();
        double soma = 0;
        while(iterator.hasNext()){
            double next = iterator.next().calcularXp();
            soma += next;
        }
        this.setTotalXp(soma);

        /*return this.conteudosConcluidos
                .stream()
                .mapToDouble(Conteudo::calcularXp)
                .sum();*/
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Set<Conteudo> getConteudosInscritos() {
        return conteudosInscritos;
    }

    public void setConteudosInscritos(Set<Conteudo> conteudosInscritos) {
        this.conteudosInscritos = conteudosInscritos;
    }

    public Set<Conteudo> getConteudosConcluidos() {
        return conteudosConcluidos;
    }

    public void setConteudosConcluidos(Set<Conteudo> conteudosConcluidos) {
        this.conteudosConcluidos = conteudosConcluidos;
    }

    public Double getTotalXp() { return totalXp; }

    public void setTotalXp(Double totalXp) {
        this.totalXp = totalXp;
    }

    public String getNomeBootcamp() { return nomeBootcamp; }

    public void setNomeBootcamp(String nomeBootcamp) {
        this.nomeBootcamp = nomeBootcamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dev dev = (Dev) o;
        return Objects.equals(nome, dev.nome) && Objects.equals(conteudosInscritos, dev.conteudosInscritos) && Objects.equals(conteudosConcluidos, dev.conteudosConcluidos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, conteudosInscritos, conteudosConcluidos);
    }

    @Override
    public String toString() {
        return "Dev{" + "nome='" + nome + "'}";
    }

    public void listarConteudosConcluidos() {
        Iterator<Conteudo> iterator = this.conteudosConcluidos.iterator();
        while(iterator.hasNext()){
            System.out.println(iterator.next().getTitulo());
        }
    }

    public void imprimirCertificadoConclusao() {
        if(this.conteudosInscritos.size() > 0) {
            System.out.println("O Dev " + this.getNome() + " ainda não concluiu todos os conteudos do " + this.getNomeBootcamp());
        } else {
            System.out.println("Certificamos que " + this.getNome() + " concluiu o " + this.getNomeBootcamp());
        }
    }

    @Override
    public int compareTo(Dev dev) {
        int totalXp = Double.compare(dev.getTotalXp(), this.getTotalXp());
        if (totalXp != 0) return totalXp;
        return this.getNome().compareTo(dev.getNome());
    }
}
