import {Component, OnInit} from '@angular/core';
import {Product} from '../services/product';

@Component({
  selector: 'app-products',
  imports: [
  ],
  templateUrl: './products.html',
  styleUrl: './products.css',
})
export class Products implements OnInit{
  products : any ;
  constructor(private productService : Product) {
  }
  ngOnInit(){
    this.getAllProducts();
  }
  getAllProducts(){
    this.productService.getAllProducts().subscribe({
      next : resp => {
        this.products = resp;
      },
      error : err => {
        console.log(err);
      }
    });
   }
   handleDeletet(product: any) {
    let v = confirm("Êtes vous sur de vouloir supprimer ce produit ?");
    if(v==true){
     this.productService.deleteProduct(product).subscribe({
       next : resp => {
         this.getAllProducts();
       },
       error : err => {
         console.log(err);
       }
     });
    }

  }
}
