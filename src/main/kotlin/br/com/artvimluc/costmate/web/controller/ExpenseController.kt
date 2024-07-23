package br.com.artvimluc.costmate.web.controller

import br.com.artvimluc.costmate.domain.Expense
import br.com.artvimluc.costmate.service.ExpenseService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/expense")
class ExpenseController

@Autowired
constructor (
    private val expenseService: ExpenseService,
) {

    @GetMapping
    fun get(): ResponseEntity<List<Expense> > {
        return ResponseEntity.ok(expenseService.find())
    }
    @GetMapping("/{id}")
    fun get(@PathVariable id: Long): ResponseEntity<Expense> {
        return ResponseEntity.ok(expenseService.find(id))
    }
    @PostMapping
    fun create(@RequestBody expense: Expense): ResponseEntity<Expense> {
        return ResponseEntity(expenseService.create(expense), HttpStatus.CREATED)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody expense: Expense): ResponseEntity<Expense> {
        return ResponseEntity(expenseService.update(id, expense), HttpStatus.OK)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Expense> {
        expenseService.delete(id)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }

    @GetMapping("/import")
    fun import() {
        return expenseService.import()
    }
}
