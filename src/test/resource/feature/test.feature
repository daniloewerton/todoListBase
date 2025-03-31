Feature: test with cucumber

  Background:

    Scenario: Quando fizer uma requisição que funcione

      When eu fizer uma requisição para "/"
      Then tem que retornar "200"