package br.com.artvimluc.costmate.web.controller;

import br.com.artvimluc.costmate.domain.CreditCard
import br.com.artvimluc.costmate.service.CreditCardService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/credit-card")
class CreditCardController

@Autowired
constructor (
    private val creditCardService: CreditCardService,
) {

    @GetMapping
    fun get(): ResponseEntity<List<CreditCard>> {
        return ResponseEntity.ok(creditCardService.find())
    }
    @GetMapping("/{id}")
    fun get(@PathVariable id: Long): ResponseEntity<CreditCard> {
        return ResponseEntity.ok(creditCardService.find(id))
    }
    @PostMapping
    fun create(@RequestBody creditCard: CreditCard): ResponseEntity<CreditCard> {
        return ResponseEntity(creditCardService.create(creditCard), HttpStatus.CREATED)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody creditCard: CreditCard): ResponseEntity<CreditCard> {
        return ResponseEntity(creditCardService.update(id, creditCard), HttpStatus.OK)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<CreditCard> {
        creditCardService.delete(id)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }
}
